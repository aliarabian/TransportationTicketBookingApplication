package com.transportation.search.boundary;

import com.transportation.airline.tickets.booking.boundry.dao.InMemoryAirlineTransportationDaoImpl;
import com.transportation.airline.tickets.booking.entity.AirlineTransportation;

public class AirlineTransportationsResource {

    private final TransportationSearchService<AirlineTransportation> transportationSearchService;

    public AirlineTransportationsResource() {
        transportationSearchService = new AirlineTransportationSearchService(new InMemoryAirlineTransportationDaoImpl());
    }

    public AirlineTransportation getTransportationById(long transportationId) {
        AirlineTransportation transportation = transportationSearchService.findTransportationById(transportationId);
        return transportation;
    }
}
