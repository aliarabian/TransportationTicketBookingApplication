package com.platform.business.service.search.transportations;

import com.platform.business.enitity.Transportation;

public interface TransportationSearchService<T extends Transportation<?, ?>> {
    T findTransportationById(Long id);
}
