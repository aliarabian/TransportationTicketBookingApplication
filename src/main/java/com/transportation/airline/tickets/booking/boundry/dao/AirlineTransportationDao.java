package com.transportation.airline.tickets.booking.boundry.dao;

import com.transportation.airline.tickets.booking.entity.AirlineTransportation;
import com.transportation.airline.tickets.booking.entity.PlaneTicket;

import java.util.Optional;

public interface AirlineTransportationDao {
    Optional<AirlineTransportation> findTransportationById(Long id);

    void persist(PlaneTicket ticket);
}
