package com.platform.business.mapper;

import com.platform.business.model.Flight;
import com.platform.business.service.transportations.dto.FlightDto;
import com.platform.business.service.transportations.dto.SeatingSectionDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FlightMapper implements Mapper<Flight, FlightDto> {
    private SeatingSectionMapper sectionMapper;

    public FlightMapper(SeatingSectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }


    @Override
    public FlightDto toDto(Flight flight) {
        Set<SeatingSectionDto> seatingSectionDtos = flight.getVehicle().getSeatingSections().stream()
                                                          .map(seatingSection -> sectionMapper.toDto(seatingSection))
                                                          .collect(Collectors.toSet());
        return new FlightDto(flight.getId(), flight.getVehicle().getModelName(),
                getAirportAndCityNames(flight.getOffset().getName(), flight.getOffset().getCity().getName()),
                getAirportAndCityNames(flight.getDestination().getName(), flight.getDestination().getCity().getName()),
                flight.getDeparturesAt().toZonedDateTime(),
                flight.availableSeats(),
                seatingSectionDtos);
    }

    private String getAirportAndCityNames(String airport, String city) {
        return airport + "/" + city;
    }
}
