package com.platform.repository.ticket;

import com.platform.business.model.FlightTicket;
import persistence.data.storage.memory.DuplicateItemException;

import java.util.Set;

public interface FlightTicketDao {

    void persist(FlightTicket ticket) throws DuplicateItemException;

    Set<FlightTicket> getAllTickets();

    Set<FlightTicket> getUsersTicketsByUsername(String username);
}