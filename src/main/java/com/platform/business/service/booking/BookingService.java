package com.platform.business.service.booking;

import com.platform.business.service.booking.dto.PlaneTicketBookingRequest;
import com.platform.business.enitity.PlaneTicket;

import java.util.Set;

public interface BookingService {
    Set<PlaneTicket> bookTickets(PlaneTicketBookingRequest req);
}
