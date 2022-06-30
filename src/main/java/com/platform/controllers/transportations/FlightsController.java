package com.platform.controllers.transportations;

import com.platform.ApiResponseEntity;
import com.platform.business.booking.dto.request.FlightPassengerDto;
import com.platform.business.service.transportations.TransportationSearchService;
import com.platform.business.service.transportations.dto.FlightDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "flights", produces = APPLICATION_JSON_VALUE)
public class FlightsController {

    private final TransportationSearchService transportationSearchService;

    @Autowired
    public FlightsController(TransportationSearchService transportationSearchService) {
        this.transportationSearchService = transportationSearchService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ApiResponseEntity<Set<FlightDto>>> findTransportations(@RequestParam(name = "offset", required = false) String offset,
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "departureTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime departureTime) {

        ApiResponseEntity<Set<FlightDto>> apiResponse;
        if (offset == null && destination == null && departureTime == null) {
            apiResponse = new ApiResponseEntity<>(transportationSearchService.findAllTransportations());
        } else {
            apiResponse = new ApiResponseEntity<>(transportationSearchService.findTransportations(offset, destination, departureTime), false);
        }
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "{flightId}")
    public ResponseEntity<ApiResponseEntity<FlightDto>> getTransportationById(@PathVariable("flightId") long flightId) {
        ApiResponseEntity<FlightDto> apiResponse;
        apiResponse = new ApiResponseEntity<>(transportationSearchService.findTransportationById(flightId), false);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{flightId}/passengers")
    public ResponseEntity<ApiResponseEntity<Set<FlightPassengerDto>>> getFlightPassengers(@PathVariable String flightId) {
        return ResponseEntity.ok()
                             .build();
    }
}
