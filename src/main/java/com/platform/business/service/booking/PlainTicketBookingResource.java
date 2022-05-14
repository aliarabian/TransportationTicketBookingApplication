package com.platform.business.service.booking;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.dto.response.PlaneTicketDto;
import com.platform.business.exception.ApplicationException;

import java.util.Set;

public class PlainTicketBookingResource {
    private final BookingService bookingService;

    public PlainTicketBookingResource(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public ResponseEntity<Set<PlaneTicketDto>> bookTickets(PlaneTicketBookingRequest request) {
        try {
            return new ResponseEntity<>(bookingService.bookTickets(request), false);
        } catch (ApplicationException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.errorCode()));
        }

    }
}
