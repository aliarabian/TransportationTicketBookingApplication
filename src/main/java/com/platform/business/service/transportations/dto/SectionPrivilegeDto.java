package com.platform.business.service.transportations.dto;

public class SectionPrivilegeDto {

    private final Long id;
    private final String description;

    public SectionPrivilegeDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SectionPrivilegeDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }
}
