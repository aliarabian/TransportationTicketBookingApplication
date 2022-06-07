package com.platform.business.service.booking;

import com.platform.business.model.*;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.FlightPassengerDto;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.PassengerExistsException;
import com.platform.mapper.PassengerMapper;
import com.platform.mapper.PlaneTicketMapper;
import com.platform.repository.country.InMemoryCountryDao;
import com.platform.repository.customer.InMemoryCustomerDao;
import com.platform.repository.ticket.InMemoryPlaneTicketDao;
import com.platform.repository.transportation.InMemoryFlightsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Given there is a flight available ")
class PlaneTicketBookingServiceTest {
    private final BookingService bookingService = new FlightTicketBookingService(
            new InMemoryFlightsDao(),
            new InMemoryPlaneTicketDao(), new InMemoryCustomerDao(),
            new PassengerMapper(new InMemoryCountryDao()), new PlaneTicketMapper());

    private PlaneTicketBookingRequest planeTicketBookingRequest;

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
            Set<FlightTicketDto> bookedTickets = bookingService.bookTickets(request);

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
            bookingService.bookTickets(request);
            assertThrows(PassengerExistsException.class, () -> bookingService.bookTickets(request));
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
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
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
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(request);
        int numberOfRequestedTickets = request.getPassengersBookingDetails().size();

        assertEquals(numberOfRequestedTickets, planeTickets.size());
    }

    @Test
    void shouldThrowRuntimeException() {

        PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
        request.setCustomerId(924427L);
        request.setTransportationId(1002L);
        request.setSeatingSectionId(710L);
        request.setPassengersBookingDetails(Set.of());
        assertThrows(RuntimeException.class, () -> bookingService.bookTickets(request));
    }

    @Test
    void shouldAddBookedTicketToCustomersBookedTicketsSet() {
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
        List<Long> ticketIds = getTicketIds(planeTickets);
        Customer customer = TransportationBookingSystemImMemoryDataSource.getCustomers().customer(planeTicketBookingRequest.getCustomerId());
        List<Long> customersBookedTicketsIds = customer.getBookedTickets().stream()
                                                       .map(Ticket::getId)
                                                       .collect(Collectors.toList());
        assertTrue(customersBookedTicketsIds.containsAll(ticketIds));
    }

    @Test
    void shouldAddBookedTicketsToTransportationsBookedTicketsSet() {

        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
        Flight transportation = TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(planeTicketBookingRequest.getTransportationId());
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
        Set<FlightTicketDto> planeTickets = bookingService.bookTickets(request);
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
        Set<FlightTicketDto> tickets = bookingService.bookTickets(request);
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
        Flight transportation = TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(request.getTransportationId());
        int oldAvailableSeats = transportation.availableSeats();
        bookingService.bookTickets(request);
        assertEquals(oldAvailableSeats - request.getPassengersBookingDetails().size(), transportation.availableSeats());
    }


    private PlaneTicketBookingRequest initializePlaneTicketBookingRequest(PlaneBookingPassengerDetail... passengersDetails) {
        PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
        request.setCustomerId(924427L);
        request.setTransportationId(1002L);
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
