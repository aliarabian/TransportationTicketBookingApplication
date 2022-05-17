package com.platform.business.service.search.transportations;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.mapper.AirlineTransportationMapper;
import com.platform.business.mapper.SeatingSectionMapper;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Set;

@RestController
public class AirlineTransportationsResource {

    private final TransportationSearchService transportationSearchService;

    @Autowired
    public AirlineTransportationsResource(TransportationSearchService transportationSearchService) {
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
