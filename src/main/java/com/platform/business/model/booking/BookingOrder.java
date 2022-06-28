package com.platform.business.model.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.business.model.Customer;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class BookingOrder {
    private final Long id;
    private final Set<FlightTicket> tickets;
    @JsonIgnore
    private final Customer customer;
    private final LocalDateTime timestamp;
    private OrderStatus status;

    public BookingOrder(Long id, Set<FlightTicket> tickets, Customer customer) {
        this.id = id;
        this.tickets = tickets;
        this.customer = customer;
        this.timestamp = LocalDateTime.now();
        this.status = OrderStatus.PENDING_PAYMENT;
    }

    public BookingOrder(Long id, Set<FlightTicket> tickets, Customer customer, LocalDateTime timestamp, OrderStatus status) {
        this.id = id;
        this.tickets = tickets;
        this.customer = customer;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Set<FlightTicket> getTickets() {
        return Collections.unmodifiableSet(tickets);
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BookingOrder updateStatus(OrderStatus status) {
        return new BookingOrder(id, Collections.unmodifiableSet(this.tickets), customer, timestamp, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingOrder order = (BookingOrder) o;
        return id.equals(order.id) && customer.equals(order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer);
    }

}
