package com.platform.business.service.search.transportations;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.AirlineTransportationSearchService;
import com.platform.business.exception.ApplicationException;
import com.platform.business.mapper.AirlineTransportationMapper;
import com.platform.business.mapper.SeatingSectionMapper;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;

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
}
