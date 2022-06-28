package com.platform.business.model.booking;

import com.platform.business.model.Customer;
import com.platform.business.model.transportation.Seat;
import com.platform.business.model.transportation.Transportation;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

public abstract class Ticket<T extends Transportation, U extends Passenger, S extends Seat> implements Serializable {
    private Long id;
    private OffsetDateTime timestamp;
    private T transportation;
    private U passenger;
    private S seat;
    private Customer customer;

    public Ticket(Long id, T transportation, U passenger, S seat, Customer customer) {
        this.id = id;
        this.timestamp = OffsetDateTime.now();
        this.passenger = passenger;
        this.transportation = transportation;
        this.customer = customer;
        this.seat = seat;
    }

    public Ticket(T transportation, U passenger, S seat, Customer customer) {
        this.transportation = transportation;
        this.customer = customer;
        this.passenger = passenger;
        this.seat = seat;
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public T getTransportation() {
        return transportation;
    }

    public U getPassenger() {
        return passenger;
    }

    public S getSeat() {
        return seat;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", transportation=" + transportation +
                ", passenger=" + passenger +
                ", seat=" + seat +
                ", customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket<?, ?, ?> ticket = (Ticket<?, ?, ?>) o;
        return id.equals(ticket.id) && transportation.getId().equals(ticket.transportation.getId())
                && passenger.getNationalId().equals(ticket.passenger.getNationalId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transportation, passenger);
    }

    public void setId(Long id) {
        this.id = id;
    }
}