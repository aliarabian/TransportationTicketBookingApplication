package com.platform.business.mapper;

import com.platform.business.enitity.AirlineTransportation;
import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;
import com.platform.business.service.search.transportations.dto.SeatingSectionDto;

import java.util.Set;
import java.util.stream.Collectors;

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
        return new AirlineTransportationDto(entity.getId(), entity.getVehicle().getModelName(), entity.getOffset().getName(), entity.getDestination().getName(),
                entity.getDeparturesAt().toZonedDateTime(),
                entity.availableSeats(),
                seatingSectionDtos);
    }
}
