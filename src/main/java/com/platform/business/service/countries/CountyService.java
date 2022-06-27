package com.platform.business.service.countries;

import com.platform.business.model.Country;
import com.platform.repository.country.CountryDao;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@Service
public class CountyService {

    private final CountryDao countryDao;

    public CountyService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }


    @Cacheable("countries")
    public List<Country> getAllCountries() {
        return countryDao.findAll();
    }
}
