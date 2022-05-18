package com.platform.repository.ticket;

import com.platform.model.FlightTicket;

public interface PlaneTicketDao {

    void persist(FlightTicket ticket);
}
