package com.platform.business.mapper;

import com.platform.business.enitity.SeatingSection;
import com.platform.business.service.search.transportations.dto.SeatingSectionDto;
import com.platform.business.service.search.transportations.dto.SectionPrivilegeDto;

import java.util.Set;
import java.util.stream.Collectors;

public class SeatingSectionMapper implements Mapper<SeatingSection, SeatingSectionDto> {
    @Override
    public SeatingSection fromDto(SeatingSectionDto dto) {
        return null;
    }

    @Override
    public SeatingSectionDto toDto(SeatingSection entity) {
        Set<SectionPrivilegeDto> privileges = getSectionPrivilegeDtos(entity);
        return new SeatingSectionDto(entity.getId(), entity.title(), entity.getAvailableSeats(), privileges);
    }

    private Set<SectionPrivilegeDto> getSectionPrivilegeDtos(SeatingSection entity) {
        return entity.getSectionPrivileges().stream()
                     .map(privilege -> new SectionPrivilegeDto(privilege.getId(), privilege.getServiceDescription()))
                     .collect(Collectors.toSet());
    }
}
