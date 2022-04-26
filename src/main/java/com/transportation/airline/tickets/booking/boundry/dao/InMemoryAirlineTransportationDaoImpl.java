package com.transportation.airline.tickets.booking.boundry.dao;

import com.transportation.airline.tickets.booking.entity.*;
import sample.data.TestDataSource;

import java.util.Optional;

public class InMemoryAirlineTransportationDaoImpl implements AirlineTransportationDao {

    public InMemoryAirlineTransportationDaoImpl() {
    }

    @Override
    public Optional<AirlineTransportation> findTransportationById(Long id) {
        return Optional.ofNullable(TestDataSource.airlineTransportations.transportation(id));
    }
}
