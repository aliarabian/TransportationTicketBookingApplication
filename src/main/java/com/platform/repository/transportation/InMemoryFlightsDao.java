package com.platform.repository.transportation;

import com.platform.business.model.transportation.Flight;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryFlightsDao implements FlightsDao {

    public InMemoryFlightsDao() {
    }

    @Override
    public Optional<Flight> findTransportationById(Long id) {
        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().transportation(id));
    }

    @Override
    public Set<Flight> findTransportationsByDate(OffsetDateTime dateTime) {
        return TransportationBookingSystemImMemoryDataSource.getAirlineTransportations().findTransportationsByDate(dateTime);
    }

    @Override
    public Set<Flight> findAllTransportations() {
        return TransportationBookingSystemImMemoryDataSource.getAirlineTransportations()
                                                            .findAllTransportations().stream()
                                                            .filter(airlineTransportation -> airlineTransportation.getDeparturesAt().compareTo(OffsetDateTime.now(airlineTransportation.getDeparturesAt().getOffset())) >= 0)
                                                            .collect(Collectors.toSet());
    }

    @Override
    public Set<Flight> findTransportations(String from, String to, OffsetDateTime dateTime) {
        return TransportationBookingSystemImMemoryDataSource.getAirlineTransportations()
                                                            .findAllTransportations().stream()
                                                            .filter(isMatch(from, to, dateTime))
                                                            .collect(Collectors.toSet());
    }

    private Predicate<Flight> isMatch(String from, String to, OffsetDateTime dateTime) {
        return airlineTransportation -> isMatch(from, to, dateTime, airlineTransportation);
    }

    private boolean isMatch(String from, String to, OffsetDateTime dateTime, Flight airlineTransportation) {
        return airlineTransportation.getOffset().getCity().getName().equals(from) &&
                airlineTransportation.getDestination().getCity().getName().equals(to) &&
                airlineTransportation.getDeparturesAt().toLocalDate().equals(dateTime.toLocalDate());
    }

}
