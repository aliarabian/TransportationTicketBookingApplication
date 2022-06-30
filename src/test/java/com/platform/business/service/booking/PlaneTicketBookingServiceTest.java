package com.platform.business.service.booking;

import com.platform.business.booking.BookingService;
import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.business.booking.dto.request.FlightPassengerDto;
import com.platform.business.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.booking.entity.Ticket;
import com.platform.business.booking.exception.PassengerExistsException;
import com.platform.business.model.Customer;
import com.platform.business.model.transportation.Flight;
import com.platform.business.model.transportation.SeatingSection;
import com.platform.business.model.transportation.SeatingSectionPrivilege;
import org.junit.jupiter.api.*;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@DisplayName("Given there is a flight available ")
class PlaneTicketBookingServiceTest {
    private final BookingService bookingService;

    private PlaneTicketBookingRequest planeTicketBookingRequest;

    PlaneTicketBookingServiceTest(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Nested
    @DisplayName("When booking ticket for multiple passengers ")
    class MultipleTicketBookingTest {

        @Test
        @DisplayName("Then it should not add a passenger twice to the same flight")
        void testAddingMultiplePassengersResultsInOnlyAddingEachPassengerOnlyOnceToTheFlight() {
            LocalDate localDate = LocalDate.of(1998, 4, 12);
            FlightPassengerDto passengerNumberOneFirstInstance = generatePassengerDto("Amir", "A", "87946621",
                    localDate, "00254976321",
                    LocalDate.of(2023, 12, 15));
            FlightPassengerDto passengerNumberOneSecondInstance = generatePassengerDto("Amir", "A", "87946621", localDate,
                    "00254976321",
                    LocalDate.of(2023, 12, 15));
            FlightPassengerDto passengerNumberOneThirdInstance = generatePassengerDto("Amir", "A", "87946621", localDate,
                    "00254976321",
                    LocalDate.of(2023, 12, 15));
            localDate = LocalDate.of(1984, 7, 16);
            FlightPassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "87946622",
                    localDate, "00254976322",
                    LocalDate.of(2023, 12, 15));
            PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOneFirstInstance),
                    getPlaneBookingPassengerDetail(passengerNumberOneSecondInstance),
                    getPlaneBookingPassengerDetail(passengerNumberOneThirdInstance),
                    getPlaneBookingPassengerDetail(passengerNumberTwo));
            Set<FlightTicketDto> bookedTickets = bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L);

            assertEquals(2, bookedTickets.size());
        }
    }

    @Nested
    @DisplayName("When booking ticket for a single passenger ")
    class SingleTicketBookingTest {

        @Test
        @DisplayName("Then it should not add a passenger twice to the same flight")
        void shouldNotAddPassengerTwice() {
            LocalDate localDate = LocalDate.of(1998, 4, 12);
            FlightPassengerDto passengerNumberOneFirstInstance = generatePassengerDto("Amir", "A", "87946621",
                    localDate, "00254976321",
                    LocalDate.of(2023, 12, 15));
            PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOneFirstInstance));
            bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L);
            assertThrows(PassengerExistsException.class, () -> bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L));
        }
    }

    @BeforeEach
    void setUp() {

        FlightPassengerDto passengerDto = generatePassengerDto("Ali", "Arabian", "425833154",
                LocalDate.of(1989, 11, 2),
                "00089795421113", LocalDate.of(2022, 10, 14));
        planeTicketBookingRequest = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerDto));
    }

    @Test
    void shouldBookOneTicket() {
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(planeTicketBookingRequest, "ali.arabian@gmail.com", 1002L);
        assertEquals(1, planeTickets.size());
    }

    @Test
    void shouldBookAllRequestTickets() {
        FlightPassengerDto passengerNumberOne = generatePassengerDto("Amir", "A", "879462621",
                LocalDate.of(1995, 12, 1), "0025491276321",
                LocalDate.of(2023, 12, 15));
        FlightPassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "879463621",
                LocalDate.of(2000, 2, 22), "0025431976321",
                LocalDate.of(2023, 12, 15));
        FlightPassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "879446621",
                LocalDate.of(1984, 5, 13), "002543976321",
                LocalDate.of(2023, 12, 15));
        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOne),
                getPlaneBookingPassengerDetail(passengerNumberTwo), getPlaneBookingPassengerDetail(passengerNumberThree));
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L);
        int numberOfRequestedTickets = request.getPassengersBookingDetails().size();

        assertEquals(numberOfRequestedTickets, planeTickets.size());
    }

    @Test
    void shouldThrowRuntimeException() {

        PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
        request.setSeatingSectionId(710L);
        request.setPassengersBookingDetails(Set.of());
        assertThrows(RuntimeException.class, () -> bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L));
    }

    @Test
    void shouldAddBookedTicketToCustomersBookedTicketsSet() {
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(planeTicketBookingRequest, "ali.arabian@gmail.com", 1002L);
        List<Long> ticketIds = getTicketIds(planeTickets);
        Customer customer = TransportationBookingSystemImMemoryDataSource.getCustomers().customer(9224427L);
        List<Long> customersBookedTicketsIds = customer.getBookedTickets().stream()
                                                       .map(Ticket::getId)
                                                       .collect(Collectors.toList());
        assertTrue(customersBookedTicketsIds.containsAll(ticketIds));
    }

    @Test
    void shouldAddBookedTicketsToTransportationsBookedTicketsSet() {

        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(planeTicketBookingRequest, "ali.arabin@gmail.com", 1002L);
        Flight transportation = TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(1002L);
        List<Long> ticketIds = getTicketIds(planeTickets);
        List<Long> transportationBookedTicketsId = transportation.getBookedTickets().stream()
                                                                 .map(Ticket::getId)
                                                                 .collect(Collectors.toList());

        assertTrue(transportationBookedTicketsId.containsAll(ticketIds));
    }

    private List<Long> getTicketIds(Set<FlightTicketDto> planeTickets) {
        return planeTickets.stream()
                           .map(FlightTicketDto::getTicketId)
                           .collect(Collectors.toList());
    }

    @Test
    void shouldAddSeatingSectionPrivilegesOfRequestSeatingSection() {
        FlightPassengerDto passengerDto = generatePassengerDto("Ali", "Arabian", "425833154",
                LocalDate.of(1994, 3, 1),
                "00089795421113",
                LocalDate.of(2023, 12, 15));

        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerDto));
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L);
        FlightTicketDto planeTicketDto = planeTickets.stream().findFirst().get();
        SeatingSection seatingSection = TransportationBookingSystemImMemoryDataSource.getSeatingSections().seatingSection(planeTicketDto.getSectionId());
        Set<String> sectionPrivileges = seatingSection.getSectionPrivileges().stream()
                                                      .map(SeatingSectionPrivilege::getServiceDescription)
                                                      .collect(Collectors.toSet());
        long ignoredPrivileges = planeTickets.stream()
                                             .map(FlightTicketDto::getSelectedPrivileges)
                                             .flatMap(Collection::stream)
                                             .distinct()
                                             .filter(privilege -> !sectionPrivileges.contains(privilege))
                                             .count();
        assertEquals(0, ignoredPrivileges);
    }

    @Test
    void shouldAddPassengersToTheSameSection() {
        FlightPassengerDto passengerNumberOne = generatePassengerDto("Amir", "A", "87946621",
                LocalDate.of(1995, 12, 1), "00254976321",
                LocalDate.of(2023, 12, 15));
        FlightPassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "879121446621",
                LocalDate.of(2000, 2, 22), "00254121976321",
                LocalDate.of(2023, 12, 15));
        FlightPassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "8794622621",
                LocalDate.of(1984, 5, 13), "0025124176321",
                LocalDate.of(2023, 12, 15));
        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOne),
                getPlaneBookingPassengerDetail(passengerNumberTwo), getPlaneBookingPassengerDetail(passengerNumberThree));
        Set<FlightTicketDto> tickets = bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L);
        long count = tickets.stream()
                            .map(FlightTicketDto::getSectionTitle)
                            .distinct()
                            .count();
        assertEquals(1, count);
    }

    @Test
    void testTransportationAvailableSeats() {
        FlightPassengerDto passengerNumberOne = generatePassengerDto("Amir", "A", "87946621",
                LocalDate.of(1995, 12, 1), "00254976321",
                LocalDate.of(2023, 12, 15));
        FlightPassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "879121446621",
                LocalDate.of(2000, 2, 22), "00254121976321",
                LocalDate.of(2023, 12, 15));
        FlightPassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "8794612621",
                LocalDate.of(1984, 5, 13), "0025124976321",
                LocalDate.of(2023, 12, 15));
        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOne),
                getPlaneBookingPassengerDetail(passengerNumberTwo), getPlaneBookingPassengerDetail(passengerNumberThree));
        Flight flight = TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(1002L);
        int oldAvailableSeats = flight.availableSeats();
        bookingService.bookTickets(request, "ali.arabian@gmail.com", 1002L);
        assertEquals(oldAvailableSeats - request.getPassengersBookingDetails().size(), flight.availableSeats());
    }


    private PlaneTicketBookingRequest initializePlaneTicketBookingRequest(PlaneBookingPassengerDetail... passengersDetails) {
        PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
        request.setSeatingSectionId(710L);
        request.setPassengersBookingDetails(Set.of(passengersDetails));
        return request;
    }

    private PlaneBookingPassengerDetail getPlaneBookingPassengerDetail(FlightPassengerDto passengerDto) {
        PlaneBookingPassengerDetail ticketBookingDetail = new PlaneBookingPassengerDetail();
        ticketBookingDetail.setPassenger(passengerDto);
        ticketBookingDetail.setSeatingSectionPrivilegeIds(Set.of(901L, 903234L));
        return ticketBookingDetail;
    }

    private FlightPassengerDto generatePassengerDto(String firstName, String lastName, String nationalId, LocalDate birthdate,
                                                    String passportNO, LocalDate passportExpirationDate) {
        FlightPassengerDto passengerDto = new FlightPassengerDto();
        passengerDto.setFirstName(firstName);
        passengerDto.setLastName(lastName);
        passengerDto.setNationalIdNO(nationalId);
        passengerDto.setPassportNO(passportNO);
        passengerDto.setPassportIssuingCountryCode("IR");
        passengerDto.setPassportExpirationDate(passportExpirationDate);
        passengerDto.setBirthdate(birthdate);
        return passengerDto;
    }

}
