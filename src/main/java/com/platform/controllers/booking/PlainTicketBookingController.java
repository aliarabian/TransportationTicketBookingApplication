package com.platform.controllers.booking;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.business.booking.BookingService;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.booking.dto.response.PlaneTicketDto;
import com.platform.business.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("bookings")
public class PlainTicketBookingController {
    private final BookingService bookingService;

    @Autowired
    public PlainTicketBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public ApiResponseEntity<Set<PlaneTicketDto>> bookTickets(PlaneTicketBookingRequest request) {
        try {
            return new ApiResponseEntity<>(bookingService.bookTickets(request), false);
        } catch (ApplicationException ex) {
            return new ApiResponseEntity<>(new ApiErrorResponse(ex.getMessage(), ex.errorCode()));
        }

    }
}
