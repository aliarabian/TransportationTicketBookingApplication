package com.platform.business.search.transportations;

import com.platform.model.AirlineTransportation;
import com.platform.business.exception.TransportationNotFoundException;
import com.platform.mapper.AirlineTransportationMapper;
import com.platform.business.search.transportations.dto.AirlineTransportationDto;
import com.platform.repository.transportation.AirlineTransportationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirlineTransportationSearchService implements TransportationSearchService {
    private final AirlineTransportationDao transportationDao;
    private final AirlineTransportationMapper transportationMapper;

    @Autowired
    public AirlineTransportationSearchService(AirlineTransportationDao transportationDao, AirlineTransportationMapper transportationMapper) {
        this.transportationDao = transportationDao;
        this.transportationMapper = transportationMapper;
    }

    @Override
    public AirlineTransportationDto findTransportationById(Long id) {
        AirlineTransportation airlineTransportation = transportationDao.findTransportationById(id)
                                                                       .orElseThrow(() -> new TransportationNotFoundException("Wrong transportation number"));

        return transportationMapper.toDto(airlineTransportation);
    }

    @Override
    public Set<AirlineTransportationDto> findTransportation(OffsetDateTime dateTime) {
        Set<AirlineTransportation> transportationsByDate = transportationDao.findTransportationsByDate(dateTime);
        return transportationsByDate.stream()
                                    .map(transportationMapper::toDto)
                                    .collect(Collectors.toSet());
    }

    @Override
    public Set<AirlineTransportationDto> findAllTransportations() {
        Set<AirlineTransportation> transportations = transportationDao.findAllTransportations();
        return transportations.stream()
                              .map(transportationMapper::toDto)
                              .collect(Collectors.toSet());
    }

    @Override
    public Set<AirlineTransportationDto> findTransportations(String from, String to, OffsetDateTime dateTime) {
        Set<AirlineTransportation> transportations = transportationDao.findTransportations(from, to, dateTime);
        return transportations.stream()
                              .map(transportationMapper::toDto)
                              .collect(Collectors.toSet());
    }
}
