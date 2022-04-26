package com.transportation.airline.tickets.booking.boundry;

import com.transportation.airline.tickets.booking.boundry.dto.PlaneTicketBookingRequest;
import com.transportation.airline.tickets.booking.entity.PlaneTicket;

import java.util.Set;

public interface BookingService {
    Set<PlaneTicket> bookTickets(PlaneTicketBookingRequest req);
}
