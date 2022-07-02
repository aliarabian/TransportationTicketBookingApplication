package com.platform.business.service.transportations.dto;

import com.platform.business.model.transportation.PlaneSeat;

import java.util.Collections;
import java.util.Set;

public class SeatingSectionDto {
    private final Long id;
    private final String title;
    private final int availableSeats;
    private final Set<PlaneSeat> seats;
    private final Set<SectionPrivilegeDto> privileges;


    public SeatingSectionDto(Long id, String title, int availableSeats, Set<PlaneSeat> seats, Set<SectionPrivilegeDto> privileges) {
        this.id = id;
        this.title = title;
        this.availableSeats = availableSeats;
        this.seats = seats;
        this.privileges = Collections.unmodifiableSet(privileges);
    }

    public Long getId() {
        return id;
    }


    public Set<SectionPrivilegeDto> getPrivileges() {
        return privileges;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public String toString() {
        return "SeatingSectionDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", availableSeats=" + availableSeats +
                ", privileges=" + privileges +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public Set<PlaneSeat> getSeats() {
        return seats;
    }
}
