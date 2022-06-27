package com.platform.controllers.terminals;

import com.platform.business.model.Terminal;
import org.springframework.stereotype.Service;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;
import springfox.documentation.annotations.Cacheable;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirportService implements TerminalService {
    @Override
    @Cacheable("terminals")
    public Set<Terminal> terminals() {
        return TransportationBookingSystemImMemoryDataSource.getTerminals().terminals()
                                                            .stream()
                                                            .collect(Collectors.toUnmodifiableSet());
    }
}
