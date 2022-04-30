package com.platform.repository.country;

import com.platform.business.enitity.Country;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Optional;

public class InMemoryCountryDao implements CountryDao {
    @Override
    public Optional<Country> findByCountryCode(String code) {

        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getCountries().country(code));
    }
}
