package com.platform.repository.transportation;

import com.platform.enitity.AirlineTransportation;
import com.platform.enitity.PlaneTicket;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Optional;

public class InMemoryAirlineTransportationDao implements AirlineTransportationDao {

    public InMemoryAirlineTransportationDao() {
    }

    @Override
    public Optional<AirlineTransportation> findTransportationById(Long id) {
        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(id));
    }

    @Override
    public void persist(PlaneTicket ticket) {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTicket(ticket);
    }
}
