package com.platform.business.mapper;

import com.platform.business.model.FlightTicket;
import com.platform.business.model.SeatingSectionPrivilege;
import com.platform.business.service.booking.dto.FlightTicketDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FlightTicketMapper implements Mapper<FlightTicket, FlightTicketDto> {

    @Override
    public FlightTicketDto toDto(FlightTicket planeTicket) {

        FlightTicketDto planeTicketDto = new FlightTicketDto();
        planeTicketDto.setPassportNO(planeTicket.getPassenger().getPassportDetails().getPassportNO());
        planeTicketDto.setOffset(planeTicket.getTransportation().getOffset().getName() + "/" +
                planeTicket.getTransportation().getOffset().getCity().getName());
        planeTicketDto.setDestination(planeTicket.getTransportation().getDestination().getName() + "/" +
                planeTicket.getTransportation().getDestination().getCity().getName());
        planeTicketDto.setPassengerName(planeTicket.getPassenger().getFirstName() + " " + planeTicket.getPassenger().getLastName());
        planeTicketDto.setDepartureDateTime(planeTicket.getTransportation().getDeparturesAt().toZonedDateTime());
        planeTicketDto.setSectionTitle(planeTicket.getSeat().getSection().title());
        planeTicketDto.setSeatNO(planeTicket.getSeat().getSeatNO());
        planeTicketDto.setTransportationId(planeTicket.getTransportation().getId());
        planeTicketDto.setAge(planeTicket.getPassenger()
                                         .getBirthdate()
                                         .until(LocalDate.now())
                                         .getYears());
        planeTicketDto.setTicketId(planeTicket.getId());
        Set<String> privilegesDescriptions = planeTicket.getSelectedPrivileges().stream()
                                                        .map(SeatingSectionPrivilege::getServiceDescription)
                                                        .collect(Collectors.toUnmodifiableSet());
        planeTicketDto.setSelectedPrivileges(privilegesDescriptions);
        planeTicketDto.setSectionId(planeTicket.getSeat().getSection().getId());
        return planeTicketDto;
    }
}
