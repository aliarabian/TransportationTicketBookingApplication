package com.platform.repository.ticket;

import com.platform.business.model.FlightTicket;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

@Repository
public class InMemoryPlaneTicketDao implements PlaneTicketDao {
    @Override
    public void persist(FlightTicket ticket) {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTicket(ticket);
    }
}
