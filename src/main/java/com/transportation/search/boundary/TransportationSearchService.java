package com.transportation.search.boundary;

import com.transportation.airline.tickets.booking.entity.Transportation;

public interface TransportationSearchService<T extends Transportation> {
    T findTransportationById(Long id);
}
