package com.transportation.airline.tickets.booking.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

public abstract class Ticket<T extends Transportation, U extends Passenger, S extends Seat> implements Serializable {
    private Long id;
    private ZonedDateTime timestamp;
    private T transportation;
    private U passenger;
    private S seat;
    private Customer customer;

    public Ticket(Long id, T transportation, U passenger, S seat, Customer customer) {
        this.id = id;
        this.timestamp = ZonedDateTime.now();
        this.passenger = passenger;
        this.transportation = transportation;
        this.customer = customer;
        this.seat = seat;
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getTimestamp() {
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
}