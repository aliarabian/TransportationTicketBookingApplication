package com.platform.business.service.booking;

import com.platform.business.exception.CustomerNotFoundException;
import com.platform.business.exception.TransportationNotFoundException;
import com.platform.business.mapper.Mapper;
import com.platform.business.model.*;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.FlightPassengerDto;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.BookingException;
import com.platform.business.service.booking.exception.PassengerExistsException;
import com.platform.business.service.booking.exception.TransportationHappenedBeforeException;
import com.platform.repository.country.CountryDao;
import com.platform.repository.customer.CustomerDao;
import com.platform.repository.ticket.FlightTicketDao;
import com.platform.repository.transportation.FlightsDao;
import org.springframework.stereotype.Service;
import persistence.data.storage.memory.DuplicateItemException;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlightTicketBookingService implements BookingService {
    private final FlightsDao flightsDao;
    private final FlightTicketDao ticketDao;
    private final CountryDao countryDao;
    private final CustomerDao customerDao;
    private final Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper;

    public FlightTicketBookingService(FlightsDao flightsDao, FlightTicketDao ticketDao,
            CountryDao countryDao, CustomerDao customerDao,
            Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper) {

        this.flightsDao = flightsDao;
        this.ticketDao = ticketDao;
        this.countryDao = countryDao;
        this.customerDao = customerDao;
        this.ticketDtoMapper = ticketDtoMapper;
    }


    @Override
    public Set<FlightTicketDto> getAllBookings() {
        return ticketDao.getAllTickets().stream()
                        .map(ticketDtoMapper::toDto)
                        .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException {
        Objects.requireNonNull(request);
        Objects.requireNonNull(username);
        Objects.requireNonNull(flightId);

        Flight flight = flightsDao.findTransportationById(flightId)
                                  .orElseThrow(() -> new TransportationNotFoundException("Wrong Transportation Number"));
        if (flight.getDeparturesAt().isBefore(OffsetDateTime.now(flight.getDeparturesAt().getOffset()))) {
            throw new TransportationHappenedBeforeException("Flight Time has passed.");
        }
        Customer customer = customerDao.findCustomerByUsername(username)
                                       .orElseThrow(() -> new CustomerNotFoundException("Customer Doesn't Exists"));

        return bookTickets(request, flight, customer);
    }

    private Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req, Flight flight, Customer customer) {
        Set<FlightTicketDto> flightTicketDtos = new TreeSet<>(Comparator.comparing(FlightTicketDto::getPassportNO));
        Set<FlightTicket> tickets = new HashSet<>();
        try {
            for (PlaneBookingPassengerDetail bookingDetail : req.getPassengersBookingDetails()) {
                PlaneSeat seat = flight.bookSeat(req.getSeatingSectionId());
                tickets.add(createPlaneTicket(flight, customer, bookingDetail, seat));
            }
            ticketDao.persist(tickets);
            flight.addNewBookings(tickets);
            customer.addTickets(tickets);
            tickets.forEach(ticket -> flightTicketDtos.add(ticketDtoMapper.toDto(ticket)));
        } catch (DuplicateItemException e) {
            throw new PassengerExistsException("Passenger Has Already Booked A Ticket For This Flight!");
        }
        return flightTicketDtos;
    }

    private FlightTicket createPlaneTicket(Flight flight, Customer customer, PlaneBookingPassengerDetail bookingDetail, PlaneSeat seat) {
        Set<SeatingSectionPrivilege> privileges = seat.getSection().retrievePrivilegesById(bookingDetail.getSeatingSectionPrivilegeIds());
        return new FlightTicket(
                flight, privileges, toPassenger(bookingDetail.getPassenger()),
                seat, customer);
    }

    private FlightPassenger toPassenger(FlightPassengerDto passengerDto) {
        Country country = countryDao.findByCountryCode(passengerDto.getPassportIssuingCountryCode())
                                    .orElseThrow();
        Passport passport = new Passport(Long.valueOf(passengerDto.getPassportNO()), passengerDto.getPassportNO(), passengerDto.getPassportExpirationDate(), country);
        return new FlightPassenger(Long.valueOf(passengerDto.getNationalIdNO()), passengerDto.getFirstName(), passengerDto.getLastName()
                , passengerDto.getNationalIdNO(), passengerDto.getBirthdate(), passport);
    }
}
