package com.platform.repository.transportation;

import com.platform.business.enitity.AirlineTransportation;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

public interface AirlineTransportationDao {
    Optional<AirlineTransportation> findTransportationById(Long id);

    Set<AirlineTransportation> findTransportationsByDate(OffsetDateTime dateTime);

    Set<AirlineTransportation> findAllTransportations();

    Set<AirlineTransportation> findTransportations(String from, String to, OffsetDateTime dateTime);
}
