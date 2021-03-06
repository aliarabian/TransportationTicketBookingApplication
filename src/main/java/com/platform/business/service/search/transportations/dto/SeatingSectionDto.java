package com.platform.business.service.search.transportations.dto;

import java.util.Collections;
import java.util.Set;

public class SeatingSectionDto {
    private Long id;
    private String title;
    private int availableSeats;
    private Set<SectionPrivilegeDto> privileges;


    public SeatingSectionDto(Long id, String title, int availableSeats, Set<SectionPrivilegeDto> privileges) {
        this.id = id;
        this.title = title;
        this.availableSeats = availableSeats;
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

    public String getTitle() {
        return title;
    }
}
