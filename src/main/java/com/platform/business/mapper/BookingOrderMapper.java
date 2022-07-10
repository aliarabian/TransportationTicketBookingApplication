package com.platform.business.mapper;

import com.platform.business.booking.dto.BookingOrderDto;
import com.platform.business.booking.dto.FlightTicketDto;
import com.platform.business.booking.entity.BookingOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingOrderMapper implements Mapper<BookingOrder, BookingOrderDto> {
    private final FlightTicketMapper ticketMapper;

    @Value("${seat.hold.timeout}")
    private long countdown;

    public BookingOrderMapper(FlightTicketMapper ticketMapper) {
        this.ticketMapper = ticketMapper;
    }

    @Override
    public BookingOrderDto toDto(BookingOrder order) {
        BookingOrderDto orderDto = new BookingOrderDto();
        orderDto.setFirstName(order.getCustomer().getFirstName());
        orderDto.setLastName(order.getCustomer().getLastName());
        orderDto.setUsername(order.getCustomer().getUsername());
        orderDto.setId(order.getId());
        orderDto.setFlightId(order.getFlight().getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        Set<FlightTicketDto> tickets = order.getTickets()
                                            .stream()
                                            .map(ticketMapper::toDto).collect(Collectors.toUnmodifiableSet());
        orderDto.setTickets(tickets);
        orderDto.setOrderCheckoutTimeLimit(countdown);
        return orderDto;
    }
}
