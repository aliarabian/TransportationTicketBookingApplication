package com.platform.repository.transportation;

import com.platform.business.enitity.AirlineTransportation;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryAirlineTransportationDao implements AirlineTransportationDao {

    public InMemoryAirlineTransportationDao() {
    }

    @Override
    public Optional<AirlineTransportation> findTransportationById(Long id) {
        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(id));
    }

    @Override
    public Set<AirlineTransportation> findTransportationsByDate(OffsetDateTime dateTime) {
        return TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().findTransportationsByDate(dateTime);
    }

    @Override
    public Set<AirlineTransportation> findAllTransportations() {
        return TransportationBookingSystemImMemoryDataSource.getAirlineTransportations()
                                                            .findAllTransportations().stream()
                                                            .filter(airlineTransportation -> airlineTransportation.getDeparturesAt().compareTo(OffsetDateTime.now(airlineTransportation.getDeparturesAt().getOffset())) >= 0)
                                                            .collect(Collectors.toSet());
    }

    @Override
    public Set<AirlineTransportation> findTransportations(String from, String to, OffsetDateTime dateTime) {
        return TransportationBookingSystemImMemoryDataSource.getAirlineTransportations()
                                                            .findAllTransportations().stream()
                                                            .filter(isMatch(from, to, dateTime))
                                                            .collect(Collectors.toSet());
    }

    private Predicate<AirlineTransportation> isMatch(String from, String to, OffsetDateTime dateTime) {
        return airlineTransportation -> isMatch(from, to, dateTime, airlineTransportation);
    }

    private boolean isMatch(String from, String to, OffsetDateTime dateTime, AirlineTransportation airlineTransportation) {
        return airlineTransportation.getOffset().getCity().getName().equals(from) &&
                airlineTransportation.getDestination().getCity().getName().equals(to) &&
                airlineTransportation.getDeparturesAt().equals(dateTime);
    }

}
