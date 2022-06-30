package com.platform.business.booking.entity;

import com.platform.business.model.Customer;
import com.platform.business.model.transportation.Flight;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class BookingOrder {
    private final Long id;
//    @JsonIgnore
    private final Set<FlightTicket> tickets;
    private final Customer customer;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final OrderStatus status;

    public BookingOrder(Long id, Set<FlightTicket> tickets, Customer customer) {
        Objects.requireNonNull(tickets);
        if (tickets.size() == 0) {
            throw new IllegalArgumentException("Tickets set must have at least one item.");
        }
        this.id = id;
        this.tickets = tickets;
        this.customer = customer;
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING_PAYMENT;
        this.updatedAt = this.createdAt;
    }

    private BookingOrder(Long id, Set<FlightTicket> tickets, Customer customer, LocalDateTime timestamp, LocalDateTime updatedAt, OrderStatus status) {
        this.id = id;
        this.tickets = tickets;
        this.customer = customer;
        this.createdAt = timestamp;
        this.updatedAt = updatedAt;
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
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BookingOrder updateStatus(OrderStatus status) {
        return new BookingOrder(id, Collections.unmodifiableSet(this.tickets), customer, createdAt, LocalDateTime.now(), status);
    }

    public Flight getFlight() {
        return this.tickets.stream().findFirst().get().getTransportation();
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
