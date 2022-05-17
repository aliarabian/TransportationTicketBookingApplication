package com.platform.business.booking;

import com.platform.business.booking.dto.response.PlaneTicketDto;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.booking.exception.BookingException;

import java.util.Set;

public interface BookingService {
    Set<PlaneTicketDto> bookTickets(PlaneTicketBookingRequest req) throws BookingException;
}
