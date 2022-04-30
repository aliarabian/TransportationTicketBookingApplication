package com.platform.business.service.search.transportations.dto;

public class SectionPrivilegeDto {

    private Long id;
    private String description;

    public SectionPrivilegeDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
