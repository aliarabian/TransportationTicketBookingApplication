package com.platform.business.service.booking;

import com.platform.business.exception.CustomerNotFoundException;
import com.platform.business.exception.TransportationNotFoundException;
import com.platform.business.model.*;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.FlightPassengerDto;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.BookingException;
import com.platform.business.service.booking.exception.PassengerExistsException;
import com.platform.business.service.booking.exception.TransportationHappenedBeforeException;
import com.platform.business.mapper.Mapper;
import com.platform.repository.country.CountryDao;
import com.platform.repository.customer.CustomerDao;
import com.platform.repository.ticket.FlightTicketDao;
import com.platform.repository.transportation.FlightsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Mapper<FlightPassenger, FlightPassengerDto> passengerMapper;
    private final Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper;
    private final Logger logger = LoggerFactory.getLogger(FlightTicketBookingService.class);

    public FlightTicketBookingService(FlightsDao flightsDao, FlightTicketDao ticketDao, CountryDao countryDao, CustomerDao customerDao, Mapper<FlightPassenger, FlightPassengerDto> passengerMapper, Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper) {
        this.flightsDao = flightsDao;
        this.ticketDao = ticketDao;
        this.countryDao = countryDao;
        this.customerDao = customerDao;
        this.passengerMapper = passengerMapper;
        this.ticketDtoMapper = ticketDtoMapper;
    }

    @Override
    public Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req) throws BookingException, NullPointerException {
        Objects.requireNonNull(req);
        Flight airlineTransportation = flightsDao.findTransportationById(req.getTransportationId())
                                                 .orElseThrow(() -> new TransportationNotFoundException("Wrong Transportation Number"));
        boolean before = airlineTransportation.getDeparturesAt().isBefore(OffsetDateTime.now(airlineTransportation.getDeparturesAt().getOffset()));
        if (before) {
            throw new TransportationHappenedBeforeException("Flight Time has passed.");
        }
        Customer customer = customerDao.findCustomerById(req.getCustomerId())
                                       .orElseThrow(() -> new CustomerNotFoundException("Customer Doesn't Exists"));

        return bookTickets(req, airlineTransportation, customer);
    }

    @Override
    public Set<FlightTicketDto> getAllBookings() {
        return ticketDao.getAllTickets().stream()
                        .map(ticketDtoMapper::toDto)
                        .collect(Collectors.toSet());
    }

    private Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req, Flight flight, Customer customer) {
        Set<FlightTicketDto> bookedTickets = new TreeSet<>(Comparator.comparing(FlightTicketDto::getPassportNO));
        Set<FlightTicket> tickets = new HashSet<>();
        try {
            for (PlaneBookingPassengerDetail bookingDetail : req.getPassengersBookingDetails()) {
                PlaneSeat seat = flight.bookSeat(req.getSeatingSectionId());
                tickets.add(createPlaneTicket(flight, customer, bookingDetail, seat));
            }
            ticketDao.persist(tickets);
            flight.addNewBookings(tickets);
            customer.addTickets(tickets);
            tickets.forEach(ticket -> bookedTickets.add(ticketDtoMapper.toDto(ticket)));
        } catch (DuplicateItemException e) {
            throw new PassengerExistsException("Passenger Has Already Booked A Ticket For This Flight!");
        }
        return bookedTickets;
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
