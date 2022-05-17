package com.platform.model;

public class FirstClassSeatingSection extends SeatingSection {

    public FirstClassSeatingSection(Long id, int capacity, Plane plane) {
        super(id, capacity, plane);
    }

    @Override
    public String title() {
        return PlaneSectionTitle.FIRST_CLASS.printValue();
    }
}
