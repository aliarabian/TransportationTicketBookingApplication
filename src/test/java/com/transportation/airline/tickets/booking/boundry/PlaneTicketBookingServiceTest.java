package com.transportation.airline.tickets.booking.boundry;

import com.platform.business.booking.BookingService;
import com.platform.business.booking.PlaneTicketBookingService;
import com.platform.repository.customer.InMemoryCustomerDao;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;
import com.platform.business.booking.dto.PlaneBookingPassengerDetail;
import com.platform.business.booking.dto.PlanePassengerDto;
import com.platform.business.booking.dto.PlaneTicketBookingRequest;
import com.platform.business.booking.PassengerMapperImpl;
import com.platform.enitity.AirlineTransportation;
import com.platform.enitity.Customer;
import com.platform.enitity.PlaneTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTicketBookingServiceTest {
    private final BookingService bookingService = new PlaneTicketBookingService(new InMemoryAirlineTransportationDao(),
            new InMemoryCustomerDao(),
            new PassengerMapperImpl());

    private PlaneTicketBookingRequest planeTicketBookingRequest;

    @BeforeEach
    void setUp() {

        PlanePassengerDto passengerDto = generatePassengerDto("Ali", "Arabian", "425833154",
                "00089795421113", "IR", ZonedDateTime.of(2022, 10, 14,
                        0, 0, 0, 0,
                        ZoneId.systemDefault()));
        planeTicketBookingRequest = initializePlaneTicketBookingRequest(getPlaneBookingPassengerDetail(passengerDto));
    }

    @Test
    void shouldBookOneTicket() {
        Set<PlaneTicket> planeTickets = bookingService.bookTickets(planeTicketBookingRequest);
        assertEquals(1, planeTickets.size());
    }

    @Test
    void shouldBookAllRequestTickets() {
        PlanePassengerDto passengerNumberOne = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                ZonedDateTime.of(2023, 12, 15, 0, 0, 0, 0, ZoneId.of("Asia/Tehran")));
        PlanePassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                ZonedDateTime.of(2023, 12, 15, 0, 0, 0, 0, ZoneId.of("Asia/Tehran")));
        PlanePassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                ZonedDateTime.of(2023, 12, 15, 0, 0, 0, 0, ZoneId.of("Asia/Tehran")));
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
                "00089795421113", "IR", ZonedDateTime.of(2022, 10, 14,
                        0, 0, 0, 0,
                        ZoneId.systemDefault()));
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
                ZonedDateTime.of(2023, 12, 15, 0, 0, 0, 0, ZoneId.of("Asia/Tehran")));
        PlanePassengerDto passengerNumberTwo = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                ZonedDateTime.of(2023, 12, 15, 0, 0, 0, 0, ZoneId.of("Asia/Tehran")));
        PlanePassengerDto passengerNumberThree = generatePassengerDto("Amir", "A", "87946621", "00254976321", "IR",
                ZonedDateTime.of(2023, 12, 15, 0, 0, 0, 0, ZoneId.of("Asia/Tehran")));
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
            String passportNO, String passportIssuingCountryCode, ZonedDateTime passportExpirationDate) {
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
