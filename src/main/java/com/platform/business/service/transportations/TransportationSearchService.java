package com.platform.business.service.transportations;

import com.platform.business.service.transportations.dto.FlightDto;

import java.time.OffsetDateTime;
import java.util.Set;

public interface TransportationSearchService {
    FlightDto findTransportationById(Long id);

    Set<FlightDto> findTransportation(OffsetDateTime dateTime);

    Set<FlightDto> findAllTransportations();

    Set<FlightDto> findTransportations(String from, String to, OffsetDateTime dateTime);
}
