package com.platform.controllers.transportations;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.search.transportations.TransportationSearchService;
import com.platform.business.search.transportations.dto.AirlineTransportationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ApiResponseEntity<Set<AirlineTransportationDto>> findTransportations(@RequestParam(name = "offset", required = false) String offset,
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "departureTime", required = false) @DateTimeFormat(pattern = "yyyy") OffsetDateTime departureTime) {
        if (offset == null && destination == null && departureTime == null) {
            return new ApiResponseEntity<>(transportationSearchService.findAllTransportations());
        }
        return new ApiResponseEntity<>(transportationSearchService.findTransportations(offset, destination, departureTime), false);
    }

    @GetMapping("{transportationId}")
    public ApiResponseEntity<AirlineTransportationDto> getTransportationById(@PathVariable("transportationId") long transportationId) {
        try {
            return new ApiResponseEntity<>(transportationSearchService.findTransportationById(transportationId), false);
        } catch (ApplicationException applicationException) {
            return new ApiResponseEntity<>(new ApiErrorResponse(applicationException.getMessage(), applicationException.errorCode()));
        }
    }

}
