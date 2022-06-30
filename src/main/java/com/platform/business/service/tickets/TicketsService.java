package com.platform.business.service.tickets;

import com.platform.business.booking.dto.FlightTicketDto;

import java.util.List;

public interface TicketsService {

    List<FlightTicketDto> findTicketsByUsername(String username);
}
