package com.platform.repository.country;

import com.platform.business.model.Country;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryCountryDao implements CountryDao {
    @Override
    public Optional<Country> findByCountryCode(String code) {

        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getCountries().country(code));
    }

    @Override
    public List<Country> findAll() {
        return TransportationBookingSystemImMemoryDataSource
                .getCountries()
                .findAll()
                .stream()
                .collect(Collectors.toList());
    }
}
