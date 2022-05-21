package com.platform.repository.ticket;

import com.platform.business.model.FlightTicket;

public interface PlaneTicketDao {

    void persist(FlightTicket ticket);
}
