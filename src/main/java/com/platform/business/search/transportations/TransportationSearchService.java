package com.platform.business.search.transportations;

import com.platform.business.search.transportations.dto.AirlineTransportationDto;

import java.time.OffsetDateTime;
import java.util.Set;

public interface TransportationSearchService {
    AirlineTransportationDto findTransportationById(Long id);

    Set<AirlineTransportationDto> findTransportation(OffsetDateTime dateTime);

    Set<AirlineTransportationDto> findAllTransportations();

    Set<AirlineTransportationDto> findTransportations(String from, String to, OffsetDateTime dateTime);
}
