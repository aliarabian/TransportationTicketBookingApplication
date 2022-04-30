package com.platform.business.service.booking;

import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.dto.response.PlaneTicketDto;

import java.util.Set;

public class PlainTicketBookingResource {
    private BookingService bookingService;

    public PlainTicketBookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Set<PlaneTicketDto> bookTickets(PlaneTicketBookingRequest request) {
        return bookingService.bookTickets(request);
    }
}
