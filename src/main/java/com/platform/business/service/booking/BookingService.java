package com.platform.business.service.booking;

import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.BookingException;

import java.util.Set;

public interface BookingService {
    Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req) throws BookingException, NullPointerException;

    Set<FlightTicketDto> getAllBookings();
}
