package com.platform.business.search.transportations.dto;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Set;

public class AirlineTransportationDto {
    private final Long id;
    private final String vehicleModelName;
    private final String offset;
    private final String destination;
    private final ZonedDateTime departuresAt;
    private final int availableSeats;
    private final Set<SeatingSectionDto> sections;


    public AirlineTransportationDto(Long id, String vehicleModelName, String offset, String destination, ZonedDateTime departuresAt, int availableSeats, Set<SeatingSectionDto> sections) {
        this.id = id;
        this.vehicleModelName = vehicleModelName;
        this.offset = offset;
        this.destination = destination;
        this.departuresAt = departuresAt;
        this.availableSeats = availableSeats;
        this.sections = Collections.unmodifiableSet(sections);
    }

    public Long getId() {
        return id;
    }

    public String getOffset() {
        return offset;
    }

    public String getDestination() {
        return destination;
    }

    public ZonedDateTime getDeparturesAt() {
        return departuresAt;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public Set<SeatingSectionDto> getSections() {
        return sections;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }
}
