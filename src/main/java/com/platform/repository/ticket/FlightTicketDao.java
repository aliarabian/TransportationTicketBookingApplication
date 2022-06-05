package com.platform.repository.ticket;

import com.platform.business.model.FlightTicket;

import java.util.Set;

public interface FlightTicketDao {

    void persist(FlightTicket ticket);

    Set<FlightTicket> getAllTickets();

    Set<FlightTicket> getUsersTicketsByUsername(String username);
}
