package com.platform.repository.ticket;

import com.platform.business.model.FlightTicket;
import com.platform.business.service.booking.dto.FlightTicketDto;

import java.util.Set;

public interface PlaneTicketDao {

    void persist(FlightTicket ticket);

    Set<FlightTicket> getAllTickets();
}
