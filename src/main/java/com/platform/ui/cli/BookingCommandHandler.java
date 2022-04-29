package com.platform.ui.cli;

import com.platform.business.booking.BookingService;
import com.platform.business.booking.PassengerMapperImpl;
import com.platform.business.booking.PlaneTicketBookingService;
import com.platform.business.booking.dto.PlaneBookingPassengerDetail;
import com.platform.business.booking.dto.PlanePassengerDto;
import com.platform.business.booking.dto.PlaneTicketBookingRequest;
import com.platform.business.search.transportations.AirlineTransportationsResource;
import com.platform.enitity.AirlineTransportation;
import com.platform.enitity.PlaneTicket;
import com.platform.enitity.SeatingSection;
import com.platform.repository.customer.InMemoryCustomerDao;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class BookingCommandHandler implements CommandHandler {

    private final AirlineTransportationsResource transportationsResource;
    private final BookingService bookingService;

    public BookingCommandHandler() {
        transportationsResource = new AirlineTransportationsResource();
        bookingService = new PlaneTicketBookingService(new InMemoryAirlineTransportationDao(), new InMemoryCustomerDao(), new PassengerMapperImpl());
    }

    @Override
    public void handle() {
        try (Scanner scanner = new Scanner(in);
             PrintWriter writer = new PrintWriter(out)) {
            out.print("Transportation ID: ");
            writer.flush();
            try {
                long transportationId = scanner.nextLong();
                AirlineTransportation transportation = transportationsResource.getTransportationById(transportationId);
                printFlightDetails(writer, transportation);
                writer.flush();
                out.println();
                out.print("Enter Section ID: ");
                long sectionId = scanner.nextLong();
                SeatingSection section = getSection(transportation, sectionId);
                out.println(printableSectionData(section));
                out.print("Enter Number of Passengers: ");
                int numberOfPassengers = scanner.nextInt();
                Set<PlaneBookingPassengerDetail> passengerDetails = getPlaneBookingPassengerDetails(scanner, section, numberOfPassengers);

                PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
                request.setCustomerId(924427L);
                request.setTransportationId(transportationId);
                request.setSeatingSectionId(sectionId);
                request.setPassengersBookingDetails(passengerDetails);
                Set<PlaneTicket> planeTickets = bookingService.bookTickets(request);
                out.println(planeTickets);
            } catch (InputMismatchException inputMismatchException) {
                err.println("Invalid Transportation Id");
                exit(-1);
            }

        }
    }

    private Set<PlaneBookingPassengerDetail> getPlaneBookingPassengerDetails(Scanner scanner, SeatingSection section, int numberOfPassengers) {
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

    private Set<Long> getPassengersDesiredPrivileges(Scanner scanner, SeatingSection section) {
        Set<Long> selectedPrivileges = new HashSet<>();

        for (int j = 0; j < section.getSectionPrivileges().size(); j++) {
            out.print("Enter Desired Privilege ID: ");
            selectedPrivileges.add(scanner.nextLong());
            if (j == section.getSectionPrivileges().size() - 1) {
                break;
            }
            out.print("another one?(Y/N) ");
            if (scanner.next().equals("N")) {
                break;
            }
        }
        return selectedPrivileges;
    }

    private SeatingSection getSection(AirlineTransportation transportation, long sectionId) {
        return transportation.getVehicle().getSeatingSections().stream()
                             .filter(seatingSection -> seatingSection.getId().equals(sectionId))
                             .findFirst().get();
    }

    private void printFlightDetails(PrintWriter writer, AirlineTransportation transportation) {
        writer.println("Flight NO: " + transportation.getId());
        writer.println("Plane Model: " + transportation.getVehicle().getModelName());
        writer.println("Flight Time: " + transportation.getDeparturesAt().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        List<String> formattedSectionsInfo = getSectionsInfo(transportation);
        writer.println("Available Sections: ");
        formattedSectionsInfo.forEach(writer::write);
    }

    private List<String> getSectionsInfo(AirlineTransportation transportation) {
        Set<SeatingSection> seatingSections = transportation.getVehicle()
                                                            .getSeatingSections();
        List<SeatingSection> availableSections = seatingSections.stream()
                                                                .filter(seatingSection -> seatingSection.getAvailableSeats() > 0)
                                                                .collect(Collectors.toList());
        return availableSections.stream()
                                .map(this::printableSectionData)
                                .collect(Collectors.toList());
    }

    private String printableSectionData(SeatingSection section) {
        StringBuilder sb = new StringBuilder();
        sb.append("\tSection ID: ")
          .append(section.getId())
          .append("\n")
          .append("\tSection: ")
          .append(section.title())
          .append("\n")
          .append("\tAvailable Seats: ")
          .append(section.getAvailableSeats())
          .append("\n")
          .append("\tPrivileges: \n");
        if (section.getSectionPrivileges().isEmpty())
            sb.append("\t\tNo Privileges\n");
        else {
            section.getSectionPrivileges().forEach(privilege ->
                    sb.append("\t\tPrivilege Id: ")
                      .append(privilege.getId())
                      .append("\n")
                      .append("\t\tPrivilege: ")
                      .append(privilege.getServiceDescription())
                      .append("\n")
            );
        }
        return sb.toString();
    }
}
