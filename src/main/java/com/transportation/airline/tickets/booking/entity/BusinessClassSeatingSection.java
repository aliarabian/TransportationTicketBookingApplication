package com.transportation.airline.tickets.booking.entity;

public class BusinessClassSeatingSection extends SeatingSection {

    public BusinessClassSeatingSection(Long id, int capacity, Plane plane) {
        super(id, capacity, plane);
    }

    @Override
    public String title() {
        return PlaneSectionTitle.BUSINESS_CLASS.printValue();
    }
}
