package com.platform.business.search.transportations;

import com.platform.repository.transportation.AirlineTransportationDao;
import com.platform.enitity.AirlineTransportation;

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
