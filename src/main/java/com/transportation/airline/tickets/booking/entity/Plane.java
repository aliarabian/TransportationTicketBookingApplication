package com.transportation.airline.tickets.booking.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Plane extends TransportationVehicle<TransportationCompany<AirlineTransportation, Plane>> {
    private Set<SeatingSection> seatingSections;

    public Plane(Long id, int capacity, String modelName, TransportationCompany<AirlineTransportation, Plane> transportationCompany) {
        super(id, capacity, modelName, transportationCompany);
        this.seatingSections = new HashSet<>();
    }

    public void addSection(SeatingSection seatingSection) {
        Objects.requireNonNull(seatingSection);
        this.seatingSections.add(seatingSection);
    }

    public Set<SeatingSection> getSeatingSections() {
        return seatingSections;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "seatingSections=" + seatingSections +
                "} " + super.toString();
    }

    public int availableSeats() {
        return seatingSections.stream()
                              .map(SeatingSection::getAvailableSeats)
                              .reduce(0, Integer::sum);
    }
}
