package com.platform.controllers.transportations;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.search.transportations.TransportationSearchService;
import com.platform.business.search.transportations.dto.AirlineTransportationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Set;

@RestController
public class AirlineTransportationsController {

    private final TransportationSearchService transportationSearchService;

    @Autowired
    public AirlineTransportationsController(TransportationSearchService transportationSearchService) {
        this.transportationSearchService = transportationSearchService;
    }

    @GetMapping("/resources/transportations/{transportationId}")
    public ResponseEntity<AirlineTransportationDto> getTransportationById(@PathVariable("transportationId") long transportationId) {
        try {
            return new ResponseEntity<>(transportationSearchService.findTransportationById(transportationId), false);
        } catch (ApplicationException applicationException) {
            return new ResponseEntity<>(new ErrorResponse(applicationException.getMessage(), applicationException.errorCode()));
        }
    }

    public ResponseEntity<Set<AirlineTransportationDto>> getTransportationsByDate(OffsetDateTime dateTime) {
        return new ResponseEntity<>(transportationSearchService.findTransportation(dateTime), false);
    }

    public ResponseEntity<Set<AirlineTransportationDto>> getAllTransportations() {
        return new ResponseEntity<>(transportationSearchService.findAllTransportations(), false);
    }

    public ResponseEntity<Set<AirlineTransportationDto>> findTransportations(String from, String to, OffsetDateTime dateTime) {
        return new ResponseEntity<>(transportationSearchService.findTransportations(from, to, dateTime), false);
    }
}
