package com.platform.business.service.search.transportations;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.mapper.AirlineTransportationMapper;
import com.platform.business.mapper.SeatingSectionMapper;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;

import java.time.OffsetDateTime;

public class AirlineTransportationsResource {

    private final TransportationSearchService transportationSearchService;

    public AirlineTransportationsResource() {
        transportationSearchService = new AirlineTransportationSearchService(new InMemoryAirlineTransportationDao(),
                new AirlineTransportationMapper(new SeatingSectionMapper()));
    }

    public ResponseEntity<?> getTransportationById(long transportationId) {
        try {
            return new ResponseEntity<>(transportationSearchService.findTransportationById(transportationId), false);
        } catch (ApplicationException applicationException) {
            return new ResponseEntity<>(new ErrorResponse(applicationException.getMessage(), applicationException.errorCode()), true);
        }
    }

    public ResponseEntity<?> getTransportationsByDate(OffsetDateTime dateTime) {
        return new ResponseEntity<>(transportationSearchService.findTransportation(dateTime), false);
    }

    public ResponseEntity<?> getAllTransportations() {
        return new ResponseEntity<>(transportationSearchService.findAllTransportations(), false);
    }

    public ResponseEntity<?> findTransportations(String from, String to, OffsetDateTime dateTime) {
        return new ResponseEntity<>(transportationSearchService.findTransportations(from, to, dateTime), false);
    }
}
