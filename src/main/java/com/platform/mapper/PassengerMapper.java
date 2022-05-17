package com.platform.mapper;

import com.platform.model.Country;
import com.platform.model.Passport;
import com.platform.model.PlanePassenger;
import com.platform.business.booking.dto.request.PlanePassengerDto;
import com.platform.repository.country.CountryDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class PassengerMapper implements Mapper<PlanePassenger, PlanePassengerDto> {

    private final CountryDao countryDao;

    public PassengerMapper(CountryDao countryDao) {
        this.countryDao = countryDao;
    }


    @Override
    public PlanePassenger fromDto(PlanePassengerDto passengerDto) {
        Country country = countryDao.findByCountryCode(passengerDto.getPassportIssuingCountryCode())
                                    .orElseThrow();
        // TODO FIX generate passport and passenger id dynamically or assign it later
        Passport passport = new Passport(598L, passengerDto.getPassportNO(), passengerDto.getPassportExpirationDate(), country);
        return new PlanePassenger(478L, passengerDto.getFirstName(), passengerDto.getLastName()
                , passengerDto.getNationalIdNO(), passengerDto.getBirthdate(), passport);
    }

    @Override
    public PlanePassengerDto toDto(PlanePassenger passenger) {
        PlanePassengerDto passengerDto = new PlanePassengerDto();
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setLastName(passenger.getLastName());
        passengerDto.setPassportIssuingCountryCode(passenger.getPassportDetails().getIssuedIn().getName());
        passengerDto.setPassportNO(passenger.getPassportDetails().getPassportNO());
        passengerDto.setPassportExpirationDate(passenger.getPassportDetails().getExpiresAt());
        return passengerDto;
    }
}
