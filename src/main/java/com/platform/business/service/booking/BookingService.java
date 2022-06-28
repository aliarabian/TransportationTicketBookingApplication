package com.platform.business.service.booking;

import com.platform.business.model.booking.BookingOrder;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.BookingException;

import java.util.Set;

public interface BookingService {

    Set<FlightTicketDto> getAllBookings();

    Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException;

    BookingOrder bookTicketsWithRequestedSeats(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException;
}
