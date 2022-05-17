package com.platform.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Transportation<T extends TransportationVehicle<?>, U extends Ticket<?, ?, ?>> implements Serializable {
    private final Long id;
    private final Terminal offset;
    private final Terminal destination;
    private final OffsetDateTime departuresAt;
    private final T vehicle;
    private final Set<U> bookedTickets;
    private final TransportationCompany<?, ?> transportationCompany;

    protected Transportation(Long id, Terminal offset, Terminal destination, OffsetDateTime departuresAt, T vehicle) {
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

    public OffsetDateTime getDeparturesAt() {
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
