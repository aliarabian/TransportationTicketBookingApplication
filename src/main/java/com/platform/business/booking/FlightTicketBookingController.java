package com.platform.business.booking;

import com.platform.ApiResponseEntity;
import com.platform.ResourceCreationDetails;
import com.platform.business.booking.dto.BookingOrderDto;
import com.platform.business.booking.dto.CheckoutRequest;
import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.business.booking.dto.request.PlaneTicketBookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public ResponseEntity<ApiResponseEntity<Set<FlightTicketDto>>> bookTickets(@Valid @RequestBody PlaneTicketBookingRequest request,
            @PathVariable @Valid Long flightId, HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getRemoteUser();
        return ResponseEntity.ok().body(new ApiResponseEntity<>(bookingService.bookTickets(request, username, flightId)));
    }

    @PostMapping("{flightId}/seat-bookings")
    public ResponseEntity<ApiResponseEntity<BookingOrderDto>> bookRequestedSeats(@Valid @RequestBody PlaneTicketBookingRequest request
            , @PathVariable @Valid Long flightId, HttpServletRequest httpServletRequest) {

        String username = httpServletRequest.getRemoteUser();
        return ResponseEntity.ok().body(new ApiResponseEntity<>(bookingService.bookTicketsWithRequestedSeats(request, username, flightId)));
    }

    @PutMapping("{flightId}/seat-bookings")
    public ResponseEntity<ApiResponseEntity<BookingOrderDto>> checkout(@Valid @RequestBody CheckoutRequest request
            , @PathVariable @Valid Long flightId, HttpServletRequest httpServletRequest) {

        String username = httpServletRequest.getRemoteUser();
        return ResponseEntity.ok().body(new ApiResponseEntity<>(bookingService.checkout(request, username)));
    }

    @PostMapping("bookings")
    public ResponseEntity<ApiResponseEntity<ResourceCreationDetails>> changePassengersFlight() {
        return ResponseEntity.created(URI.create(""))
                             .build();
    }

    @GetMapping("bookings")
    public ResponseEntity<ApiResponseEntity<Set<FlightTicketDto>>> getFlightBookings() {
        return ResponseEntity.ok(new ApiResponseEntity<>(bookingService.getAllBookings()));
    }
}
