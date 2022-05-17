package com.platform.repository.ticket;

import com.platform.model.PlaneTicket;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

@Repository
public class InMemoryPlaneTicketDao implements PlaneTicketDao {
    @Override
    public void persist(PlaneTicket ticket) {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTicket(ticket);
    }
}
