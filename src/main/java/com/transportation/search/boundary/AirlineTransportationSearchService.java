package com.transportation.search.boundary;

import com.transportation.airline.tickets.booking.boundry.dao.AirlineTransportationDao;
import com.transportation.airline.tickets.booking.entity.AirlineTransportation;

public class AirlineTransportationSearchService implements TransportationSearchService<AirlineTransportation> {
    private AirlineTransportationDao transportationDao;

    public AirlineTransportationSearchService(AirlineTransportationDao transportationDao) {
        this.transportationDao = transportationDao;
    }

    @Override
    public AirlineTransportation findTransportationById(Long id) {
        return transportationDao.findTransportationById(id).orElseThrow(() ->
                new RuntimeException("Bad Request"));
    }
}
