package com.platform.business.booking;

import com.platform.business.booking.dto.BookingOrderDto;
import com.platform.business.booking.dto.CheckoutRequest;
import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.booking.exception.BookingException;

import java.util.Set;

public interface BookingService {

    Set<FlightTicketDto> getAllBookings();

    Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException;

    BookingOrderDto bookTicketsWithRequestedSeats(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException;

    BookingOrderDto checkout(CheckoutRequest request, String username);
}
