package com.platform.business.service.search.transportations;

import com.platform.business.enitity.AirlineTransportation;
import com.platform.repository.transportation.AirlineTransportationDao;

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
