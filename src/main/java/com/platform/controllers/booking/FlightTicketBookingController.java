package com.platform.controllers.booking;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.ResourceCreationDetails;
import com.platform.business.exception.ApplicationException;
import com.platform.business.service.booking.BookingService;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = "flights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightTicketBookingController {
    private final BookingService bookingService;

    @Autowired
    public FlightTicketBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("{flightId}/bookings")
    public ApiResponseEntity<Set<FlightTicketDto>> bookTickets(PlaneTicketBookingRequest request, @PathVariable String flightId) {
        try {
            return new ApiResponseEntity<>(bookingService.bookTickets(request), false);
        } catch (ApplicationException ex) {
            return new ApiResponseEntity<>(new ApiErrorResponse(ex.getMessage(), ex.errorCode()));
        }
    }

    @PostMapping("bookings")
    public ResponseEntity<ApiResponseEntity<ResourceCreationDetails>> changePassengersFlight() {
        return ResponseEntity.created(URI.create(""))
                             .build();
    }

    @GetMapping("bookings")
    public ResponseEntity<ApiResponseEntity<Set<FlightTicketDto>>> getFlightBookings() {
        return ResponseEntity.ok()
                             .build();
    }
}
