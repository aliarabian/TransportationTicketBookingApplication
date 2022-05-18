package com.platform.business.booking;

import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.booking.exception.BookingException;

import java.util.Set;

public interface BookingService {
    Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req) throws BookingException;
}
