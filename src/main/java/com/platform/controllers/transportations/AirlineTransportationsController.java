package com.platform.controllers.transportations;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.search.transportations.TransportationSearchService;
import com.platform.business.search.transportations.dto.AirlineTransportationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Set;

@RestController
@RequestMapping("transportations")
public class AirlineTransportationsController {

    private final TransportationSearchService transportationSearchService;

    @Autowired
    public AirlineTransportationsController(TransportationSearchService transportationSearchService) {
        this.transportationSearchService = transportationSearchService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseEntity<Set<AirlineTransportationDto>>> findTransportations(@RequestParam(name = "offset", required = false) String offset,
                                                                                                @RequestParam(name = "destination", required = false) String destination,
                                                                                                @RequestParam(name = "departureTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime departureTime) {

        ApiResponseEntity<Set<AirlineTransportationDto>> apiResponse;
        if (offset == null && destination == null && departureTime == null) {
            apiResponse = new ApiResponseEntity<>(transportationSearchService.findAllTransportations());
        } else {
            apiResponse = new ApiResponseEntity<>(transportationSearchService.findTransportations(offset, destination, departureTime), false);
        }
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("{transportationId}")
    public ResponseEntity<ApiResponseEntity<AirlineTransportationDto>> getTransportationById(@PathVariable("transportationId") long transportationId) {
        ApiResponseEntity<AirlineTransportationDto> apiResponse;
        try {
            apiResponse = new ApiResponseEntity<>(transportationSearchService.findTransportationById(transportationId), false);
            return ResponseEntity.ok(apiResponse);
        } catch (ApplicationException applicationException) {
            apiResponse = new ApiResponseEntity<>(new ApiErrorResponse(applicationException.getMessage(), applicationException.errorCode()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }

}
