package com.platform.repository.transportation;

import com.platform.business.model.Flight;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

public interface FlightsDao {
    Optional<Flight> findTransportationById(Long id);

    Set<Flight> findTransportationsByDate(OffsetDateTime dateTime);

    Set<Flight> findAllTransportations();

    Set<Flight> findTransportations(String from, String to, OffsetDateTime dateTime);
}
