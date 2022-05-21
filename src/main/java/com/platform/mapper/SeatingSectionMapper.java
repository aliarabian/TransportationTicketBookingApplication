package com.platform.mapper;

import com.platform.business.model.SeatingSection;
import com.platform.business.service.transportations.dto.SeatingSectionDto;
import com.platform.business.service.transportations.dto.SectionPrivilegeDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
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
