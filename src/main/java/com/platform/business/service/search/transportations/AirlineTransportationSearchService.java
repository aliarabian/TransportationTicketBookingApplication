package com.platform.business.service.search.transportations;

import com.platform.business.enitity.AirlineTransportation;
import com.platform.business.mapper.AirlineTransportationMapper;
import com.platform.business.service.booking.exception.TransportationNotFoundException;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import com.platform.repository.transportation.AirlineTransportationDao;

public class AirlineTransportationSearchService implements TransportationSearchService {
    private final AirlineTransportationDao transportationDao;
    private final AirlineTransportationMapper transportationMapper;

    public AirlineTransportationSearchService(AirlineTransportationDao transportationDao, AirlineTransportationMapper transportationMapper) {
        this.transportationDao = transportationDao;
        this.transportationMapper = transportationMapper;
    }

    @Override
    public AirlineTransportationDto findTransportationById(Long id) {
        AirlineTransportation airlineTransportation = transportationDao.findTransportationById(id)
                                                                       .orElseThrow(TransportationNotFoundException::new);

        return transportationMapper.toDto(airlineTransportation);
    }
}
