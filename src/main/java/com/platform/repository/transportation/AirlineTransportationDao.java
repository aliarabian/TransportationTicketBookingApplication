package com.platform.repository.transportation;

import com.platform.business.enitity.AirlineTransportation;
import com.platform.business.enitity.PlaneTicket;

import java.util.Optional;

public interface AirlineTransportationDao {
    Optional<AirlineTransportation> findTransportationById(Long id);

}
