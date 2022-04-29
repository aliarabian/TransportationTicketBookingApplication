package com.platform.repository.transportation;

import com.platform.enitity.AirlineTransportation;
import com.platform.enitity.PlaneTicket;

import java.util.Optional;

public interface AirlineTransportationDao {
    Optional<AirlineTransportation> findTransportationById(Long id);

    void persist(PlaneTicket ticket);
}
