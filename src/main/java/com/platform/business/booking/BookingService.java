package com.platform.business.booking;

import com.platform.business.booking.dto.PlaneTicketBookingRequest;
import com.platform.enitity.PlaneTicket;

import java.util.Set;

public interface BookingService {
    Set<PlaneTicket> bookTickets(PlaneTicketBookingRequest req);
}
