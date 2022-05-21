package com.platform.mapper;

import com.platform.business.model.Flight;
import com.platform.business.service.transportations.dto.FlightDto;
import com.platform.business.service.transportations.dto.SeatingSectionDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AirlineTransportationMapper implements Mapper<Flight, FlightDto> {
    private SeatingSectionMapper sectionMapper;

    public AirlineTransportationMapper(SeatingSectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }

    @Override
    public Flight fromDto(FlightDto dto) {
        return null;
    }

    @Override
    public FlightDto toDto(Flight entity) {
        Set<SeatingSectionDto> seatingSectionDtos = entity.getVehicle().getSeatingSections().stream()
                                                          .map(seatingSection -> sectionMapper.toDto(seatingSection))
                                                          .collect(Collectors.toSet());
        return new FlightDto(entity.getId(), entity.getVehicle().getModelName(),
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
