package com.platform.business.model;

import java.io.Serializable;
import java.util.Objects;

public class SeatingSectionPrivilege implements Serializable {
    private Long id;
    private String serviceDescription;
    private SeatingSection section;

    public SeatingSectionPrivilege(Long id, String serviceDescription, SeatingSection section) {
        Objects.requireNonNull(section);
        Objects.requireNonNull(serviceDescription);
        this.id = id;
        this.serviceDescription = serviceDescription;
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public SeatingSection getSection() {
        return section;
    }

    @Override
    public String toString() {
        return "SeatingSectionPrivilege{" +
                "id=" + id +
                ", serviceDescription='" + serviceDescription + '\'' +
                '}';
    }
}
