package com.platform.business.service.booking;

import com.platform.business.enitity.*;
import com.platform.business.service.booking.dto.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.PlaneTicketBookingRequest;
import com.platform.repository.customer.CustomerDao;
import com.platform.repository.transportation.AirlineTransportationDao;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class PlaneTicketBookingService implements BookingService {
    private final AirlineTransportationDao airlineTransportationDao;
    private final CustomerDao customerDao;
    private final PassengerMapper passengerMapper;

    public PlaneTicketBookingService(AirlineTransportationDao airlineTransportationDao, CustomerDao customerDao, PassengerMapper passengerMapper) {
        this.airlineTransportationDao = airlineTransportationDao;
        this.customerDao = customerDao;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public Set<PlaneTicket> bookTickets(PlaneTicketBookingRequest req) {
        Objects.requireNonNull(req);
        if (req.getPassengersBookingDetails().isEmpty()) {
            throw new RuntimeException();
        }
        AirlineTransportation airlineTransportation = airlineTransportationDao.findTransportationById(req.getTransportationId())
                                                                              .orElseThrow();
        Customer customer = customerDao.findCustomerById(req.getCustomerId())
                                       .orElseThrow(() -> new RuntimeException("Customer not found"));

        return bookTickets(req, airlineTransportation, customer);
    }

    private Set<PlaneTicket> bookTickets(PlaneTicketBookingRequest req, AirlineTransportation airlineTransportation, Customer customer) {
        Set<PlaneTicket> bookedTickets = new TreeSet<>(Comparator.comparing(p -> p.getPassenger().getNationalId()));
        for (PlaneBookingPassengerDetail bookingDetail : req.getPassengersBookingDetails()) {
            PlaneSeat seat;
            try {
                seat = airlineTransportation.bookSeat(req.getSeatingSectionId());
                Set<SeatingSectionPrivilege> privileges = seat.getSection().retrievePrivilegesById(bookingDetail.getSeatingSectionPrivilegeIds());
                PlaneTicket ticket = new PlaneTicket(ThreadLocalRandom.current().nextLong(),
                        airlineTransportation, privileges, passengerMapper.mapToPlanePassenger(bookingDetail.getPassenger()),
                        seat, customer);
                airlineTransportationDao.persist(ticket);
                bookedTickets.add(ticket);
                airlineTransportation.addNewBooking(ticket);
                customer.addTicket(ticket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bookedTickets;
    }

}
