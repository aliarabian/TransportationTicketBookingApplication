package com.platform.business.service.booking;

import com.platform.business.enitity.Country;
import com.platform.business.enitity.Passport;
import com.platform.business.enitity.PlanePassenger;
import com.platform.business.service.booking.dto.PlanePassengerDto;

import java.util.concurrent.ThreadLocalRandom;

public class PassengerMapperImpl implements PassengerMapper {
    @Override
    public PlanePassenger mapToPlanePassenger(PlanePassengerDto passengerDto) {
        Country country = new Country(ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE), passengerDto.getPassportIssuingCountryCode(), passengerDto.getPassportIssuingCountryCode());
        Passport passport = new Passport(598L, passengerDto.getPassportNO(), passengerDto.getPassportExpirationDate(), country);
        PlanePassenger passenger = new PlanePassenger(478L, passengerDto.getFirstName(), passengerDto.getLastName()
                , passengerDto.getNationalIdNO(), passengerDto.getBirthdate(), passport);
        return passenger;
    }

    @Override
    public PlanePassengerDto mapToPlanePassengerDto(PlanePassenger passenger) {
        PlanePassengerDto passengerDto = new PlanePassengerDto();
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setLastName(passenger.getLastName());
        passengerDto.setPassportIssuingCountryCode(passenger.getPassportDetails().getIssuedIn().getName());
        passengerDto.setPassportNO(passenger.getPassportDetails().getPassportNO());
        passengerDto.setPassportExpirationDate(passenger.getPassportDetails().getExpiresAt());
        return null;
    }
}