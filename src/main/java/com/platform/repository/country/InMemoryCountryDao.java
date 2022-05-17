package com.platform.repository.country;

import com.platform.model.Country;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Optional;

@Repository
public class InMemoryCountryDao implements CountryDao {
    @Override
    public Optional<Country> findByCountryCode(String code) {

        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getCountries().country(code));
    }
}
