package ui.cli;

import com.platform.ResponseEntity;
import com.platform.business.mapper.PassengerMapper;
import com.platform.business.mapper.PlaneTicketMapper;
import com.platform.business.service.booking.BookingService;
import com.platform.business.service.booking.PlainTicketBookingResource;
import com.platform.business.service.booking.PlaneTicketBookingService;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlanePassengerDto;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.search.transportations.AirlineTransportationsResource;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import com.platform.business.service.search.transportations.dto.SeatingSectionDto;
import com.platform.repository.country.InMemoryCountryDao;
import com.platform.repository.customer.InMemoryCustomerDao;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class BookingCommandHandler implements CommandHandler {

    private final AirlineTransportationsResource transportationsResource;
    private final PlainTicketBookingResource bookingResource;

    public BookingCommandHandler() {
        transportationsResource = new AirlineTransportationsResource();
        BookingService bookingService = new PlaneTicketBookingService(new InMemoryAirlineTransportationDao(),
                new InMemoryCustomerDao(),
                new PassengerMapper(new InMemoryCountryDao()),
                new PlaneTicketMapper());
        bookingResource = new PlainTicketBookingResource(bookingService);
    }

    @Override
    public void handle() {
        try (Scanner scanner = new Scanner(in)) {
            out.print("Transportation ID: ");
            try {
                long transportationId = scanner.nextLong();
                ResponseEntity<?> response = transportationsResource.getTransportationById(transportationId);
                if (response.isError()) {
                    err.println("Wrong Transportation Number");
                }
                AirlineTransportationDto transportation = (AirlineTransportationDto) response.getData();
                printFlightDetails(transportation);
                out.println();
                out.print("Enter Section ID: ");
                long sectionId = scanner.nextLong();

                Optional<SeatingSectionDto> sectionDto = getSection(transportation, sectionId);
                if (sectionDto.isEmpty()) {
                    err.println("Wrong Section Id");
                    return;
                }
                SeatingSectionDto section = sectionDto.get();
                out.println(printableSectionData(section));
                out.print("Enter Number of Passengers: ");
                int numberOfPassengers = scanner.nextInt();
                Set<PlaneBookingPassengerDetail> passengerDetails = getPlaneBookingPassengerDetails(scanner, section, numberOfPassengers);
                PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
                request.setCustomerId(924427L);
                request.setTransportationId(transportationId);
                request.setSeatingSectionId(sectionId);
                request.setPassengersBookingDetails(passengerDetails);
                ResponseEntity<?> bookingResponse = bookingResource.bookTickets(request);
                if (bookingResponse.isError()) {
                    err.println(bookingResponse.getData().toString());
                }
                out.println(bookingResponse.getData());
            } catch (InputMismatchException inputMismatchException) {
                err.println("Invalid Input Type");
            }

        }
    }

    private Set<PlaneBookingPassengerDetail> getPlaneBookingPassengerDetails(Scanner scanner, SeatingSectionDto section, int numberOfPassengers) {
        Set<PlaneBookingPassengerDetail> passengerDetails = new HashSet<>();
        for (int i = 1; i <= numberOfPassengers; i++) {
            out.println("Passenger #" + i);
            PlanePassengerDto passengerDto = new PlanePassengerDto();
            out.print("First Name: ");
            passengerDto.setFirstName(scanner.next());
            out.print("Last Name: ");
            passengerDto.setLastName(scanner.next());
            out.print("National ID: ");
            passengerDto.setNationalIdNO(scanner.next());
            out.print("Birthdate: ");
            passengerDto.setBirthdate(LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
            out.print("Passport Number: ");
            passengerDto.setPassportNO(scanner.next());
            out.print("Passport Country Code:('IR', 'TR', 'UK', 'US') ");
            passengerDto.setPassportIssuingCountryCode(scanner.next());
            out.print("Passport Expiration Date: ");
            LocalDate expirationDate = LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            passengerDto.setPassportExpirationDate(expirationDate);
            PlaneBookingPassengerDetail bookingPassengerDetail = new PlaneBookingPassengerDetail();
            bookingPassengerDetail.setPassenger(passengerDto);
            Set<Long> selectedPrivileges = getPassengersDesiredPrivileges(scanner, section);

            bookingPassengerDetail.setSeatingSectionPrivilegeIds(selectedPrivileges);
            passengerDetails.add(bookingPassengerDetail);
        }
        return passengerDetails;
    }

    private Set<Long> getPassengersDesiredPrivileges(Scanner scanner, SeatingSectionDto section) {
        Set<Long> selectedPrivileges = new HashSet<>();

        for (int j = 0; j < section.getPrivileges().size(); j++) {
            out.print("Enter Desired Privilege ID: ");
            selectedPrivileges.add(scanner.nextLong());
            if (j == section.getPrivileges().size() - 1) {
                break;
            }
            out.print("another one?(Y/N) ");
            if (scanner.next().equals("N")) {
                break;
            }
        }
        return selectedPrivileges;
    }

    private Optional<SeatingSectionDto> getSection(AirlineTransportationDto transportation, long sectionId) {
        return transportation.getSections().stream()
                             .filter(seatingSection -> seatingSection.getId().equals(sectionId))
                             .findFirst();
    }

    private void printFlightDetails(AirlineTransportationDto transportation) {
        out.println("Flight NO: " + transportation.getId());
        out.println("Plane Model: " + transportation.getVehicleModelName());
        out.println("Flight Time: " + transportation.getDeparturesAt().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        List<String> formattedSectionsInfo = getSectionsInfo(transportation);
        out.println("Available Sections: ");
        formattedSectionsInfo.forEach(out::print);
    }

    private List<String> getSectionsInfo(AirlineTransportationDto transportation) {
        Set<SeatingSectionDto> seatingSections = transportation
                .getSections();
        List<SeatingSectionDto> availableSections = seatingSections.stream()
                                                                   .filter(seatingSection -> seatingSection.getAvailableSeats() > 0)
                                                                   .collect(Collectors.toList());
        return availableSections.stream()
                                .map(this::printableSectionData)
                                .collect(Collectors.toList());
    }

    private String printableSectionData(SeatingSectionDto section) {
        StringBuilder sb = new StringBuilder();
        sb.append("\tSection ID: ")
          .append(section.getId())
          .append("\n")
          .append("\tSection: ")
          .append(section.getTitle())
          .append("\n")
          .append("\tAvailable Seats: ")
          .append(section.getAvailableSeats())
          .append("\n")
          .append("\tPrivileges: \n");
        if (section.getPrivileges().isEmpty())
            sb.append("\t\tNo Privileges\n");
        else {
            section.getPrivileges().forEach(privilege ->
                    sb.append("\t\tPrivilege Id: ")
                      .append(privilege.getId())
                      .append("\n")
                      .append("\t\tPrivilege: ")
                      .append(privilege.getDescription())
                      .append("\n")
            );
        }
        return sb.toString();
    }
}
