package com.platform.mapper;

import com.platform.model.AirlineTransportation;
import com.platform.business.search.transportations.dto.AirlineTransportationDto;
import com.platform.business.search.transportations.dto.SeatingSectionDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AirlineTransportationMapper implements Mapper<AirlineTransportation, AirlineTransportationDto> {
    private SeatingSectionMapper sectionMapper;

    public AirlineTransportationMapper(SeatingSectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }

    @Override
    public AirlineTransportation fromDto(AirlineTransportationDto dto) {
        return null;
    }

    @Override
    public AirlineTransportationDto toDto(AirlineTransportation entity) {
        Set<SeatingSectionDto> seatingSectionDtos = entity.getVehicle().getSeatingSections().stream()
                                                          .map(seatingSection -> sectionMapper.toDto(seatingSection))
                                                          .collect(Collectors.toSet());
        return new AirlineTransportationDto(entity.getId(), entity.getVehicle().getModelName(),
                getAirportAndCityNames(entity.getOffset().getName(), entity.getOffset().getCity().getName()),
                getAirportAndCityNames(entity.getDestination().getName(), entity.getDestination().getCity().getName()),
                entity.getDeparturesAt().toZonedDateTime(),
                entity.availableSeats(),
                seatingSectionDtos);
    }

    private String getAirportAndCityNames(String airport, String city) {
        return airport + "/" + city;
    }
}
