package com.platform.business.service.booking;

import com.platform.business.exception.BadRequestException;
import com.platform.business.exception.CustomerNotFoundException;
import com.platform.business.exception.TransportationNotFoundException;
import com.platform.business.model.*;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.FlightPassengerDto;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.BookingException;
import com.platform.mapper.Mapper;
import com.platform.repository.customer.CustomerDao;
import com.platform.repository.ticket.FlightTicketDao;
import com.platform.repository.transportation.FlightsDao;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class FlightTicketBookingService implements BookingService {
    private final FlightsDao airlineTransportationDao;
    private final FlightTicketDao ticketDao;
    private final CustomerDao customerDao;
    private final Mapper<FlightPassenger, FlightPassengerDto> passengerMapper;
    private final Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper;

    public FlightTicketBookingService(FlightsDao airlineTransportationDao, FlightTicketDao ticketDao, CustomerDao customerDao, Mapper<FlightPassenger, FlightPassengerDto> passengerMapper, Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper) {
        this.airlineTransportationDao = airlineTransportationDao;
        this.ticketDao = ticketDao;
        this.customerDao = customerDao;
        this.passengerMapper = passengerMapper;
        this.ticketDtoMapper = ticketDtoMapper;
    }

    @Override
    public Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req) {
        Objects.requireNonNull(req);
        if (req.getPassengersBookingDetails().isEmpty()) {
            throw new BadRequestException("No Passenger Info Provided");
        }
        Flight airlineTransportation = airlineTransportationDao.findTransportationById(req.getTransportationId())
                .orElseThrow(() -> new TransportationNotFoundException("Wrong Transportation Number"));
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

    private Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req, Flight airlineTransportation, Customer customer) {
        Set<FlightTicketDto> bookedTickets = new TreeSet<>(Comparator.comparing(FlightTicketDto::getPassportNO));
        for (PlaneBookingPassengerDetail bookingDetail : req.getPassengersBookingDetails()) {
            PlaneSeat seat;
            try {
                seat = airlineTransportation.bookSeat(req.getSeatingSectionId());
                FlightTicket ticket = createPlaneTicket(airlineTransportation, customer, bookingDetail, seat);
                ticketDao.persist(ticket);
                bookedTickets.add(ticketDtoMapper.toDto(ticket));
                airlineTransportation.addNewBooking(ticket);
                customer.addTicket(ticket);
            } catch (BookingException e) {
                e.printStackTrace();
                throw new BadRequestException(e.getMessage());
            }
        }
        return bookedTickets;
    }

    private FlightTicket createPlaneTicket(Flight airlineTransportation, Customer customer, PlaneBookingPassengerDetail bookingDetail, PlaneSeat seat) {
        Set<SeatingSectionPrivilege> privileges = seat.getSection().retrievePrivilegesById(bookingDetail.getSeatingSectionPrivilegeIds());
        return new FlightTicket(ThreadLocalRandom.current().nextLong(500, 900_000_000),
                airlineTransportation, privileges, passengerMapper.fromDto(bookingDetail.getPassenger()),
                seat, customer);
    }

}
