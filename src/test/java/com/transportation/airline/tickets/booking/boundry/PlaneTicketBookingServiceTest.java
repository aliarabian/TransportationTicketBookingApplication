package com.transportation.airline.tickets.booking.boundry;

import com.platform.business.enitity.AirlineTransportation;
import com.platform.business.enitity.Customer;
import com.platform.business.enitity.PlaneTicket;
import com.platform.business.service.booking.BookingService;
import com.platform.business.service.booking.PassengerMapperImpl;
import com.platform.business.service.booking.PlaneTicketBookingService;
import com.platform.business.service.booking.dto.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.PlanePassengerDto;
import com.platform.business.service.booking.dto.PlaneTicketBookingRequest;
import com.platform.repository.customer.InMemoryCustomerDao;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Given there is a flight available ")
class PlaneTicketBookingServiceTest {
    private final BookingService bookingService = new PlaneTicketBookingService(new InMemoryAirlineTransportationDao(),
            new InMemoryCustomerDao(),
            new PassengerMapperImpl());

    private PlaneTicketBookingRequest planeTicketBookingRequest;

    @Nested
    @DisplayName("When booking ticket for multiple passengers ")
    class MultipleTicketBookingTest {

        @Test
        @DisplayName("Then it should not add a passenger twice to the same flight")
        void testAddingMultiplePassengersResultsInOnlyAddingEachPassengerOnlyOnceToTheFlight() {
            PlanePassengerDto passengerNumberOneFirstInstance = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                    LocalDate.of(2023, 12, 15));
            PlanePassengerDto passengerNumberOneSecondInstance = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                    LocalDate.of(2023, 12, 15));
            PlanePassengerDto passengerNumberOneThirdInstance = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                    LocalDate.of(2023, 12, 15));
            PlanePassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "87946622", "00254976322", "IR",
                    LocalDate.of(2023, 12, 15));
            PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOneFirstInstance),
                    getPlaneBookingPassengerDetail(passengerNumberOneSecondInstance),
                    getPlaneBookingPassengerDetail(passengerNumberOneThirdInstance),
                    getPlaneBookingPassengerDetail(passengerNumberTwo));
            Set<PlaneTicket> bookedTickets = bookingService.bookTickets(request);

            assertEquals(2, bookedTickets.size());
        }
    }

    @BeforeEach
    void setUp() {

        PlanePassengerDto passengerDto = generatePassengerDto("Ali", "Arabian", "425833154",
                "00089795421113", "IR", LocalDate.of(2022, 10, 14));
        planeTicketBookingRequest = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerDto));
    }

    @Test
    void shouldBookOneTicket() {
        Set<PlaneTicket> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
        assertEquals(1, planeTickets.size());
    }

    @Test
    void shouldBookAllRequestTickets() {
        PlanePassengerDto passengerNumberOne = generatePassengerDto("Amir", "A", "879466221", "00254976321", "IR",
                LocalDate.of(2023, 12, 15));
        PlanePassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "879466321", "00254976321", "IR",
                LocalDate.of(2023, 12, 15));
        PlanePassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                LocalDate.of(2023, 12, 15));
        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOne),
                getPlaneBookingPassengerDetail(passengerNumberTwo), getPlaneBookingPassengerDetail(passengerNumberThree));
        Set<PlaneTicket> planeTickets = bookingService.bookTickets(request);
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
        Set<PlaneTicket> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
        Customer customer = TransportationBookingSystemImMemoryDataSource.getCustomers().customer(planeTicketBookingRequest.getCustomerId());
        assertTrue(customer.getBookedTickets().containsAll(planeTickets));
    }

    @Test
    void shouldAddBookedTicketsToTransportationsBookedTicketsSet() {

        Set<PlaneTicket> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
        AirlineTransportation transportation = TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(planeTicketBookingRequest.getTransportationId());
        assertTrue(transportation.getBookedTickets().containsAll(planeTickets));
    }

    @Test
    void shouldAddSeatingSectionPrivilegesOfRequestSeatingSection() {
        PlanePassengerDto passengerDto = generatePassengerDto("Ali", "Arabian", "425833154",
                "00089795421113", "IR",
                LocalDate.of(2023, 12, 15));
        ;
        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerDto));
        Set<PlaneTicket> planeTickets = bookingService.bookTickets(request);
        long ignoredPrivileges = planeTickets.stream()
                                             .map(PlaneTicket::getSelectedPrivileges)
                                             .flatMap(Collection::stream)
                                             .filter(privilege -> !privilege.getSection().getId().equals(request.getSeatingSectionId()))
                                             .count();
        assertEquals(0, ignoredPrivileges);
    }

    @Test
    void testTransportationAvailableSeats() {
        PlanePassengerDto passengerNumberOne = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                LocalDate.of(2023, 12, 15));
        PlanePassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                LocalDate.of(2023, 12, 15));
        PlanePassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                LocalDate.of(2023, 12, 15));
        PlaneTicketBookingRequest request = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerNumberOne),
                getPlaneBookingPassengerDetail(passengerNumberTwo), getPlaneBookingPassengerDetail(passengerNumberThree));
        AirlineTransportation transportation = TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(request.getTransportationId());
        int oldAvailableSeats = transportation.availableSeats();
        Set<PlaneTicket> planeTickets = bookingService.bookTickets(request);
        System.out.println(transportation.capacity());
        System.out.println(transportation.availableSeats());
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

    private PlaneBookingPassengerDetail getPlaneBookingPassengerDetail(PlanePassengerDto passengerDto) {
        PlaneBookingPassengerDetail ticketBookingDetail = new PlaneBookingPassengerDetail();
        ticketBookingDetail.setPassenger(passengerDto);
        ticketBookingDetail.setSeatingSectionPrivilegeIds(Set.of(901L, 903234L));
        return ticketBookingDetail;
    }

    private PlanePassengerDto generatePassengerDto(String firstName, String lastName, String nationalId,
                                                   String passportNO, String passportIssuingCountryCode, LocalDate passportExpirationDate) {
        PlanePassengerDto passengerDto = new PlanePassengerDto();
        passengerDto.setFirstName(firstName);
        passengerDto.setLastName(lastName);
        passengerDto.setNationalIdNO(nationalId);
        passengerDto.setPassportNO(passportNO);
        passengerDto.setPassportIssuingCountryCode(passportIssuingCountryCode);
        passengerDto.setPassportExpirationDate(passportExpirationDate);
        return passengerDto;
    }

}
