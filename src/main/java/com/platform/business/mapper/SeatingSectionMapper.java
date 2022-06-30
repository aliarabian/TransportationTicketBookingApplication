package com.platform.business.mapper;

import com.platform.business.model.transportation.SeatingSection;
import com.platform.business.service.transportations.dto.SeatingSectionDto;
import com.platform.business.service.transportations.dto.SectionPrivilegeDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SeatingSectionMapper implements Mapper<SeatingSection, SeatingSectionDto> {

    @Override
    public SeatingSectionDto toDto(SeatingSection seatingSection) {
        Set<SectionPrivilegeDto> privileges = getSectionPrivilegeDtos(seatingSection);
        return new SeatingSectionDto(seatingSection.getId(), seatingSection.title(), seatingSection.getAvailableSeats(), seatingSection.getSeats(), privileges);
    }

    private Set<SectionPrivilegeDto> getSectionPrivilegeDtos(SeatingSection entity) {
        return entity.getSectionPrivileges().stream()
                     .map(privilege -> new SectionPrivilegeDto(privilege.getId(), privilege.getServiceDescription()))
                     .collect(Collectors.toSet());
    }
}
