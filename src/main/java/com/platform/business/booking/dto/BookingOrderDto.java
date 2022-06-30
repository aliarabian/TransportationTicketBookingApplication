package com.platform.business.booking.dto;

import com.platform.business.booking.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class BookingOrderDto {
    private Long id;
    private Set<FlightTicketDto> tickets;
    private long flightId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime updatedAt;
    private OrderStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<FlightTicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(Set<FlightTicketDto> tickets) {
        this.tickets = tickets;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
