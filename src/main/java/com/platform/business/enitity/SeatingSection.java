package com.platform.business.enitity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SeatingSection implements Serializable {
    private Long id;
    private Set<SeatingSectionPrivilege> sectionPrivileges;
    private Set<PlaneSeat> seats;
    private Plane plane;
    private int capacity;
    private int availableSeats;

    public SeatingSection() {
    }

    public SeatingSection(Long id, int capacity, Plane plane) {
        if (capacity + plane.availableSeats() > plane.getCapacity()) {
            throw new RuntimeException();
        }
        this.id = id;
        this.plane = plane;
        this.capacity = capacity;
        this.seats = new HashSet<>();
        this.sectionPrivileges = new HashSet<>();
    }

    public Long getId() {
        return id;
    }


    public Set<SeatingSectionPrivilege> getSectionPrivileges() {
        return sectionPrivileges;
    }

    public void addSectionPrivilege(SeatingSectionPrivilege privilege) {
        Objects.requireNonNull(privilege);
        this.sectionPrivileges.add(privilege);
    }

    public Set<PlaneSeat> getSeats() {
        return seats;
    }

    public Optional<PlaneSeat> bookSeat() {
        for (PlaneSeat seat : seats) {
            if (!seat.isBooked()) {
                seat.bookSeat();
                this.availableSeats--;
                return Optional.of(seat);
            }
        }
        return Optional.empty();
    }

    /**
     * Ignores non existent requested privileges
     */
    public Set<SeatingSectionPrivilege> retrievePrivilegesById(Set<Long> requestedPrivilegesIds) {
        return this.sectionPrivileges.stream()
                                     .filter(sectionPrivilege -> requestedPrivilegesIds.contains(sectionPrivilege.getId()))
                                     .collect(Collectors.toSet());
    }

    public void addSeat(PlaneSeat seat) {
        if (availableSeats == capacity) {
            throw new RuntimeException();
        }
        Objects.requireNonNull(seat);
        this.seats.add(seat);
        availableSeats++;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public Plane getPlane() {
        return plane;
    }

    public int getCapacity() {
        return capacity;
    }

    public abstract String title();

    @Override
    public String toString() {
        return "SeatingSection{" +
                "id=" + id +
                ", sectionPrivileges=" + sectionPrivileges +
                ", seats=" + seats +
                '}';
    }
}
