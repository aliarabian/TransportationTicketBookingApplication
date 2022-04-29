package com.platform.business.search.transportations;

import com.platform.enitity.Transportation;

public interface TransportationSearchService<T extends Transportation<?, ?>> {
    T findTransportationById(Long id);
}
