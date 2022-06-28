package com.platform.repository.ticket;

import com.platform.business.model.booking.FlightTicket;
import persistence.data.storage.memory.DuplicateItemException;

import java.util.Set;

public interface FlightTicketDao {

    void persist(FlightTicket ticket) throws DuplicateItemException;

    void persist(Set<FlightTicket> tickets) throws DuplicateItemException;

    Set<FlightTicket> getAllTickets();

    Set<FlightTicket> getUsersTicketsByUsername(String username);
}
