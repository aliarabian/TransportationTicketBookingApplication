package com.platform.repository.country;

import com.platform.business.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryDao {
    Optional<Country> findByCountryCode(String code);

    List<Country> findAll();
}

