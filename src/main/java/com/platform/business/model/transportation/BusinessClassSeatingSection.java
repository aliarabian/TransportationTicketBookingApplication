package com.platform.business.model.transportation;

import com.platform.business.model.transportation.Plane;
import com.platform.business.model.transportation.PlaneSectionTitle;
import com.platform.business.model.transportation.SeatingSection;

public class BusinessClassSeatingSection extends SeatingSection {

    public BusinessClassSeatingSection(Long id, int capacity, Plane plane) {
        super(id, capacity, plane);
    }

    @Override
    public String title() {
        return PlaneSectionTitle.BUSINESS_CLASS.printValue();
    }
}
