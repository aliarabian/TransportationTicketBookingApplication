package com.transportation.airline.tickets.booking.control;

import com.transportation.airline.tickets.booking.boundry.dto.PlanePassengerDto;
import com.transportation.airline.tickets.booking.entity.Country;
import com.transportation.airline.tickets.booking.entity.Passport;
import com.transportation.airline.tickets.booking.entity.PlanePassenger;

import java.util.concurrent.ThreadLocalRandom;

public class PassengerMapperImpl implements PassengerMapper {
    @Override
    public PlanePassenger mapToPlanePassenger(PlanePassengerDto passengerDto) {
        Country country = new Country(ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE), passengerDto.getPassportIssuingCountryCode(), passengerDto.getPassportIssuingCountryCode());
        Passport passport = new Passport(598L, passengerDto.getPassportNO(), passengerDto.getPassportExpirationDate(), country);
        PlanePassenger passenger = new PlanePassenger(478L, passengerDto.getFirstName(), passengerDto.getLastName()
                , passengerDto.getNationalIdNO(), passport);
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
