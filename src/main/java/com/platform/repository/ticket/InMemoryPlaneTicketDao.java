package com.platform.repository.ticket;

import com.platform.business.enitity.PlaneTicket;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

public class InMemoryPlaneTicketDao implements PlaneTicketDao {
    @Override
    public void persist(PlaneTicket ticket) {
        TransportationBookingSystemImMemoryDataSource.getTickets().addTicket(ticket);
    }
}
