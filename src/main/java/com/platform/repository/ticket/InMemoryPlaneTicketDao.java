package com.platform.repository.ticket;

import com.platform.business.booking.entity.FlightTicket;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.DuplicateItemException;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.HashSet;
import java.util.Set;

@Repository
public class InMemoryPlaneTicketDao implements FlightTicketDao {
    @Override
    public void persist(FlightTicket ticket) throws DuplicateItemException {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTicket(ticket);
    }

    @Override
    public void persist(Set<FlightTicket> tickets) throws DuplicateItemException {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTickets(tickets);
    }

    @Override
    public Set<FlightTicket> getAllTickets() {
        return new HashSet<>(TransportationBookingSystemImMemoryDataSource.getTickets().
                                                                          getAllTickets());
    }

    @Override
    public Set<FlightTicket> getUsersTicketsByUsername(String username) {
        return TransportationBookingSystemImMemoryDataSource.getTickets().getUsersTicketsByUsername(username);
    }
}
