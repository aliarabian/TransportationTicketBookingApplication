package com.platform.controllers.booking;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.booking.BookingService;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.booking.dto.response.PlaneTicketDto;
import com.platform.business.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class PlainTicketBookingController {
    private final BookingService bookingService;

    @Autowired
    public PlainTicketBookingController(BookingService bookingService) {
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
