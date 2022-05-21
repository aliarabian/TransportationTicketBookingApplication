package com.platform.business.model;

public enum PlaneSectionTitle {
    ECONOMY_CLASS("Economy Class"), BUSINESS_CLASS("Business Class"), FIRST_CLASS("First Class");
    private final String printValue;

    PlaneSectionTitle(String value) {
        printValue = value;
    }

    public String printValue() {
        return printValue;
    }
}
