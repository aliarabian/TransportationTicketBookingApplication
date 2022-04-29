package com.platform.business.enitity;

public class EconomyClassSeatingSection extends SeatingSection {

    public EconomyClassSeatingSection(Long id, int capacity, Plane plane) {
        super(id, capacity, plane);
    }

    @Override
    public String title() {
        return PlaneSectionTitle.ECONOMY_CLASS.printValue();
    }
}
