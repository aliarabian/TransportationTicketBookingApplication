package com.platform.mapper;

import com.platform.business.model.Country;
import com.platform.business.model.FlightPassenger;
import com.platform.business.model.Passport;
import com.platform.business.service.booking.dto.request.FlightPassengerDto;
import com.platform.repository.country.CountryDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class PassengerMapper implements Mapper<FlightPassenger, FlightPassengerDto> {

    private final CountryDao countryDao;

    public PassengerMapper(CountryDao countryDao) {
        this.countryDao = countryDao;
    }


    @Override
    public FlightPassenger fromDto(FlightPassengerDto passengerDto) {
        Country country = countryDao.findByCountryCode(passengerDto.getPassportIssuingCountryCode())
                                    .orElseThrow();
        Passport passport = new Passport(Long.valueOf(passengerDto.getPassportNO()), passengerDto.getPassportNO(), passengerDto.getPassportExpirationDate(), country);
        return new FlightPassenger(Long.valueOf(passengerDto.getNationalIdNO()), passengerDto.getFirstName(), passengerDto.getLastName()
                , passengerDto.getNationalIdNO(), passengerDto.getBirthdate(), passport);
    }

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
