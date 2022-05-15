package com.platform.business.service.search.transportations;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.mapper.AirlineTransportationMapper;
import com.platform.business.mapper.SeatingSectionMapper;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;

import java.time.OffsetDateTime;
import java.util.Set;

public class AirlineTransportationsResource {

    private final TransportationSearchService transportationSearchService;

    public AirlineTransportationsResource() {
        transportationSearchService = new AirlineTransportationSearchService(new InMemoryAirlineTransportationDao(),
                new AirlineTransportationMapper(new SeatingSectionMapper()));
    }

    public ResponseEntity<AirlineTransportationDto> getTransportationById(long transportationId) {
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
