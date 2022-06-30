package com.platform.business.service.tickets;

import com.platform.business.mapper.Mapper;
import com.platform.business.booking.entity.FlightTicket;
import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.repository.ticket.FlightTicketDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlightTicketsService implements TicketsService {
    private final FlightTicketDao ticketDao;
    private final Mapper<FlightTicket, FlightTicketDto> mapper;

    public FlightTicketsService(FlightTicketDao ticketDao, Mapper<FlightTicket, FlightTicketDto> mapper) {
        this.ticketDao = ticketDao;
        this.mapper = mapper;
    }

    @Override
    public List<FlightTicketDto> findTicketsByUsername(String username) {
        Objects.requireNonNull(username);
        Set<FlightTicket> userTickets = ticketDao.getUsersTicketsByUsername(username);
        return userTickets.stream().map(mapper::toDto)
                          .collect(Collectors.toList());

    }
}
