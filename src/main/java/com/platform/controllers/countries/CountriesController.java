package com.platform.controllers.countries;

import com.platform.ApiResponseEntity;
import com.platform.business.model.Country;
import com.platform.business.service.countries.CountyService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountriesController {


    private final CountyService countyService;


    public CountriesController(CountyService countyService) {
        this.countyService = countyService;
    }

    @GetMapping(value = "countries", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponseEntity<List<Country>>> getAllCountries() {
        return ResponseEntity.ok(new ApiResponseEntity<>(countyService.getAllCountries()));
    }
}
