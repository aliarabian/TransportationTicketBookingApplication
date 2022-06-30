package com.platform.business.service.transportations;

import com.platform.business.exception.TransportationNotFoundException;
import com.platform.business.mapper.FlightMapper;
import com.platform.business.model.transportation.Flight;
import com.platform.business.service.transportations.dto.FlightDto;
import com.platform.repository.transportation.FlightsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlightSearchService implements TransportationSearchService {
    private final FlightsDao transportationDao;
    private final FlightMapper transportationMapper;

    @Autowired
    public FlightSearchService(FlightsDao transportationDao, FlightMapper transportationMapper) {
        this.transportationDao = transportationDao;
        this.transportationMapper = transportationMapper;
    }

    @Override
    public FlightDto findTransportationById(Long id) {
        Flight airlineTransportation = transportationDao.findTransportationById(id)
                                                        .orElseThrow(() -> new TransportationNotFoundException("Wrong transportation number"));

        return transportationMapper.toDto(airlineTransportation);
    }

    @Override
    public Set<FlightDto> findTransportation(OffsetDateTime dateTime) {
        Set<Flight> transportationsByDate = transportationDao.findTransportationsByDate(dateTime);
        return transportationsByDate.stream()
                                    .map(transportationMapper::toDto)
                                    .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = "flights")
    public Set<FlightDto> findAllTransportations() {
        Set<Flight> transportations = transportationDao.findAllTransportations();
        return transportations.stream()
                              .map(transportationMapper::toDto)
                              .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightDto> findTransportations(String from, String to, OffsetDateTime dateTime) {
        Set<Flight> transportations = transportationDao.findTransportations(from, to, dateTime);
        return transportations.stream()
                              .map(transportationMapper::toDto)
                              .collect(Collectors.toSet());
    }
}
