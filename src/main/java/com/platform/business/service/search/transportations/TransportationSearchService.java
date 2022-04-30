package com.platform.business.service.search.transportations;

import com.platform.business.service.search.transportations.dto.AirlineTransportationDto;

public interface TransportationSearchService {
    AirlineTransportationDto findTransportationById(Long id);
}
