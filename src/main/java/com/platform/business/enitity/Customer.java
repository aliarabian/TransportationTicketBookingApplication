package com.platform.business.enitity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Customer extends User {
    private String nationalId;
    private Set<Ticket> bookedTickets;

    public Customer(Long id, String username, String firstName, String lastName, String nationalId) {
        super(id, username, firstName, lastName);
        Objects.requireNonNull(nationalId);
        if (nationalId.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.nationalId = nationalId;
        this.bookedTickets = new HashSet<>();
    }

    public String getNationalId() {
        return nationalId;
    }

    public void addTicket(Ticket ticket) {
        Objects.requireNonNull(ticket);
        this.bookedTickets.add(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return nationalId.equals(customer.nationalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalId);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "nationalId='" + nationalId + '\'' +
                "} " + super.toString();
    }

    public Set<Ticket> getBookedTickets() {
        return bookedTickets;
    }
}
