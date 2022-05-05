package com.platform.business.service.booking;

import com.platform.business.enitity.*;
import com.platform.business.exception.BadRequestException;
import com.platform.business.exception.CustomerNotFoundException;
import com.platform.business.exception.TransportationNotFoundException;
import com.platform.business.mapper.Mapper;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlanePassengerDto;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.dto.response.PlaneTicketDto;
import com.platform.business.service.booking.exception.BookingException;
import com.platform.repository.customer.CustomerDao;
import com.platform.repository.ticket.PlaneTicketDao;
import com.platform.repository.transportation.AirlineTransportationDao;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class PlaneTicketBookingService implements BookingService {
    private final AirlineTransportationDao airlineTransportationDao;
    private final PlaneTicketDao ticketDao;
    private final CustomerDao customerDao;
    private final Mapper<PlanePassenger, PlanePassengerDto> passengerMapper;
    private final Mapper<PlaneTicket, PlaneTicketDto> ticketDtoMapper;

    public PlaneTicketBookingService(AirlineTransportationDao airlineTransportationDao, PlaneTicketDao ticketDao, CustomerDao customerDao, Mapper<PlanePassenger, PlanePassengerDto> passengerMapper, Mapper<PlaneTicket, PlaneTicketDto> ticketDtoMapper) {
        this.airlineTransportationDao = airlineTransportationDao;
        this.ticketDao = ticketDao;
        this.customerDao = customerDao;
        this.passengerMapper = passengerMapper;
        this.ticketDtoMapper = ticketDtoMapper;
    }

    @Override
    public Set<PlaneTicketDto> bookTickets(PlaneTicketBookingRequest req) {
        Objects.requireNonNull(req);
        if (req.getPassengersBookingDetails().isEmpty()) {
            throw new BadRequestException("No Passenger Info Provided");
        }
        AirlineTransportation airlineTransportation = airlineTransportationDao.findTransportationById(req.getTransportationId())
                                                                              .orElseThrow(() -> new TransportationNotFoundException("Wrong Transportation Number"));
        Customer customer = customerDao.findCustomerById(req.getCustomerId())
                                       .orElseThrow(() -> new CustomerNotFoundException("Customer Doesn't Exists"));

        return bookTickets(req, airlineTransportation, customer);
    }

    private Set<PlaneTicketDto> bookTickets(PlaneTicketBookingRequest req, AirlineTransportation airlineTransportation, Customer customer) {
        Set<PlaneTicketDto> bookedTickets = new TreeSet<>(Comparator.comparing(PlaneTicketDto::getPassportNO));
        for (PlaneBookingPassengerDetail bookingDetail : req.getPassengersBookingDetails()) {
            PlaneSeat seat;
            try {
                seat = airlineTransportation.bookSeat(req.getSeatingSectionId());
                PlaneTicket ticket = createPlaneTicket(airlineTransportation, customer, bookingDetail, seat);
                ticketDao.persist(ticket);
                bookedTickets.add(ticketDtoMapper.toDto(ticket));
                airlineTransportation.addNewBooking(ticket);
                customer.addTicket(ticket);
            } catch (BookingException e) {
                e.printStackTrace();
            }
        }
        return bookedTickets;
    }

    private PlaneTicket createPlaneTicket(AirlineTransportation airlineTransportation, Customer customer, PlaneBookingPassengerDetail bookingDetail, PlaneSeat seat) {
        Set<SeatingSectionPrivilege> privileges = seat.getSection().retrievePrivilegesById(bookingDetail.getSeatingSectionPrivilegeIds());
        return new PlaneTicket(ThreadLocalRandom.current().nextLong(),
                airlineTransportation, privileges, passengerMapper.fromDto(bookingDetail.getPassenger()),
                seat, customer);
    }

}
