package com.platform.business.search.transportations;

import com.platform.enitity.AirlineTransportation;
import com.platform.repository.transportation.InMemoryAirlineTransportationDao;

public class AirlineTransportationsResource {

    private final TransportationSearchService<AirlineTransportation> transportationSearchService;

    public AirlineTransportationsResource() {
        transportationSearchService = new AirlineTransportationSearchService(new InMemoryAirlineTransportationDao());
    }

    public AirlineTransportation getTransportationById(long transportationId) {
        return transportationSearchService.findTransportationById(transportationId);
    }
}
