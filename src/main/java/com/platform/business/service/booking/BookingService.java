package com.platform.business.service.booking;

import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.dto.response.PlaneTicketDto;
import com.platform.business.service.booking.exception.BookingException;

import java.util.Set;

public interface BookingService {
    Set<PlaneTicketDto> bookTickets(PlaneTicketBookingRequest req) throws BookingException;
}
