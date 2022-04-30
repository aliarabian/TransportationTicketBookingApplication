package com.platform.business.service.search.transportations;

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

    public AirlineTransportationDto getTransportationById(long transportationId) {
        return transportationSearchService.findTransportationById(transportationId);
    }
}
