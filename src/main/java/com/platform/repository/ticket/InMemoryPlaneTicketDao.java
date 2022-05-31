package com.platform.repository.ticket;

import com.platform.business.model.FlightTicket;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class InMemoryPlaneTicketDao implements PlaneTicketDao {
    @Override
    public void persist(FlightTicket ticket) {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTicket(ticket);
    }

    @Override
    public Set<FlightTicket> getAllTickets() {
        return TransportationBookingSystemImMemoryDataSource.getTickets().
                                                            getAllTickets()
                                                            .stream()
                                                            .collect(Collectors.toSet());
    }
}
