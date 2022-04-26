package com.transportation.airline.tickets.booking.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Transportation<T extends TransportationVehicle, U extends Ticket> implements Serializable {
    private Long id;
    private Terminal offset;
    private Terminal destination;
    private ZonedDateTime departuresAt;
    private T vehicle;
    private Set<U> bookedTickets;
    private TransportationCompany<? extends Transportation, T> transportationCompany;

    public Transportation(Long id, Terminal offset, Terminal destination, ZonedDateTime departuresAt, T vehicle) {
        this.id = id;
        this.offset = offset;
        this.destination = destination;
        this.departuresAt = departuresAt;
        this.vehicle = vehicle;
        this.transportationCompany = vehicle.getTransportationCompany();
        this.bookedTickets = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Terminal getOffset() {
        return offset;
    }

    public Terminal getDestination() {
        return destination;
    }

    public Set<U> getBookedTickets() {
        return bookedTickets;
    }

    public ZonedDateTime getDeparturesAt() {
        return departuresAt;
    }

    public void addNewBooking(U ticket) {
        Objects.requireNonNull(ticket);
        this.bookedTickets.add(ticket);
    }

    public T getVehicle() {
        return vehicle;
    }

    public int capacity() {
        return vehicle.getCapacity();
    }

    @Override
    public String toString() {
        return "Transportation{" +
                "id=" + id +
                ", offset=" + offset +
                ", destination=" + destination +
                ", departuresAt=" + departuresAt +
                ", vehicle=" + vehicle +
                ", transportationCompany=" + transportationCompany +
                '}';
    }

}
