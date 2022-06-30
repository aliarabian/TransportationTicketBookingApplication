package com.platform.business.mapper;

import com.platform.business.model.transportation.FlightPassenger;
import com.platform.business.booking.dto.request.FlightPassengerDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class PassengerMapper implements Mapper<FlightPassenger, FlightPassengerDto> {


    @Override
    public FlightPassengerDto toDto(FlightPassenger passenger) {
        FlightPassengerDto passengerDto = new FlightPassengerDto();
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setLastName(passenger.getLastName());
        passengerDto.setPassportIssuingCountryCode(passenger.getPassportDetails().getIssuedIn().getName());
        passengerDto.setPassportNO(passenger.getPassportDetails().getPassportNO());
        passengerDto.setPassportExpirationDate(passenger.getPassportDetails().getExpiresAt());
        return passengerDto;
    }
}
