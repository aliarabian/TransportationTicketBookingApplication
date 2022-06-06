package persistence.data.storage.memory;

import com.platform.business.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class TransportationBookingSystemImMemoryDataSource implements Serializable {
    private static CityDataSource cities;
    private static CountryDataSource countries;
    private static AirlineTransportationCompanyDatasource airlineTransportationCompanies;
    private static PlaneDataSource planes;
    private static AirlineTransportationDataSource airlineTransportations;
    private static PlaneSeatDataSource seats;
    private static SeatingSectionDataSource seatingSections;
    private static CustomerDataSource customers;
    private static SeatingSectionPrivilegeDataSource seatingSectionPrivileges;
    private static TerminalDataSource terminals;
    private static PlaneTicketDataSource tickets;

    static {
        if (Files.exists(Paths.get("app.data"))) {
            loadDate();
        } else {
            init();
        }

    }

    private static void init() {
        countries = new CountryDataSource();
        cities = new CityDataSource();
        terminals = new TerminalDataSource();
        airlineTransportationCompanies = new AirlineTransportationCompanyDatasource();
        planes = new PlaneDataSource();
        airlineTransportations = new AirlineTransportationDataSource();
        seatingSections = new SeatingSectionDataSource();
        seats = new PlaneSeatDataSource();
        seatingSectionPrivileges = new SeatingSectionPrivilegeDataSource();
        customers = new CustomerDataSource();
        tickets = new PlaneTicketDataSource();
    }


    private static class AirlineTransportationCompanyDatasource implements Serializable {

        private final Map<Long, TransportationCompany<Flight, Plane>> airlines = new HashMap<>(Map.of(
                500L, new TransportationCompany<>(500L, "Homa", getCities().city(334L)),
                501L, new TransportationCompany<>(501L, "Iran Air", getCities().city(334L)),
                502L, new TransportationCompany<>(502L, "Hafez", getCities().city(335L)),
                503L, new TransportationCompany<>(500L, "Emirates", getCities().city(337L)),
                504L, new TransportationCompany<>(504L, "Turkish Airlines", getCities().city(338L)),
                505L, new TransportationCompany<>(505L, "Ankara Ocaklari", getCities().city(339L)),
                506L, new TransportationCompany<>(505L, "Anadolu Airlines", getCities().city(340L))
        ));

        public TransportationCompany<Flight, Plane> company(Long id) {
            return airlines.get(id);
        }
    }

    public static class AirlineTransportationDataSource implements Serializable {
        private final Map<Long, Flight> transportations;

        public AirlineTransportationDataSource() {
            transportations = new HashMap<>();
            LocalDateTime localDateTime = LocalDateTime.of(2022, 6, 12, 5, 10, 0, 0);
            ZoneId zoneId = ZoneId.of("UTC");
            transportations.put(1001L, new Flight(1001L,
                    getTerminals().terminal(400L),
                    getTerminals().terminal(402L),
                    OffsetDateTime.of(localDateTime, zoneId.getRules().getOffset(localDateTime))
                    , getPlanes().plane(600L))
            );
            localDateTime = LocalDateTime.of(2022, 6, 27, 6, 45, 0, 0);
            transportations.put(1002L, new Flight(1002L,
                    getTerminals().terminal(401L),
                    getTerminals().terminal(402L),
                    OffsetDateTime.of(localDateTime, zoneId.getRules().getOffset(localDateTime))
                    , getPlanes().plane(607L))
            );
        }

        public Flight transportation(Long id) {
            return transportations.get(id);
        }

        public Set<Flight> findTransportationsByDate(OffsetDateTime dateTime) {
            return transportations.values().stream()
                                  .filter(transportation -> transportation.getDeparturesAt().equals(dateTime))
                                  .collect(Collectors.toSet());
        }

        public Set<Flight> findAllTransportations() {
            return Set.copyOf(transportations.values());
        }
    }

    public static class CityDataSource implements Serializable {
        private final Map<Long, City> cities;

        public CityDataSource() {
            cities = new HashMap<>(Map.of(
                    333L, new City(333L, "Zanjan", getCountries().country("IR")),
                    334L, new City(334L, "Tehran", getCountries().country("IR")),
                    335L, new City(335L, "Shiraaz", getCountries().country("IR")),
                    336L, new City(336L, "London", getCountries().country("UK")),
                    337L, new City(337L, "Paris", getCountries().country("FR")),
                    338L, new City(338L, "Istanbul", getCountries().country("TR")),
                    339L, new City(339L, "Ankara", getCountries().country("TR")),
                    340L, new City(340L, "Anadolu", getCountries().country("TR"))
            ));
        }

        public City city(Long id) {
            return cities.get(id);
        }

    }

    public static class CountryDataSource implements Serializable {
        private final Map<String, Country> countries = new HashMap<>(Map.of(
                "IR", new Country(221L, "IR", "Iran"),
                "UK", new Country(222L, "UK", "United Kingdom"),
                "FR", new Country(223L, "FR", "France"),
                "TR", new Country(224L, "TR", "Turkey")
        ));

        public Country country(String code) {
            return countries.get(code);
        }

        public Collection<Country> findAll() {
            return countries.values();
        }
    }

    public static class CustomerDataSource implements Serializable {
        private final Map<Long, Customer> customers = new HashMap<>(Map.of(
                924427L, new Customer(924427L, "ali.arabian@gmail.com", "$2a$11$wCuJw0/HfW7OjAc9KLEkWOAKoBsHkfYsuupgImdkXFYFYPEAVJ79K", "Ali", "Arabian", "4986350641"),
                924429L, new Customer(924429L, "Faraanak.ghm@gmail.com", "12345", "Faraanak", "Gholaami", "44886450641"),
                924430L, new Customer(924430L, "laleh.malekian@gmail.com", "12345", "Laleh", "Malekian", "00651298772"),
                924431L, new Customer(924431L, "alihosseini@gmail.com", "12345", "Ali", "Hosseini", "98752136987"),
                924432L, new Customer(924432L, "elhamkebriya@gmail.com", "12345", "Elham", "Kebriyayi", "45925300140"),
                924433L, new Customer(924433L, "abbasi.nazli@gmail.com", "12345", "Nazli", "Abbasi", "01215480006"),
                924434L, new Customer(924434L, "homi.k@gmail.com", "12345", "Homayoun", "kazemi", "23423220887"),
                924435L, new Customer(924435L, "kaboli.m@gmail.com", "12345", "Mahdi", "Kaboli", "87799544552"),
                924436L, new Customer(924436L, "pedramrazaghi@gmail.com", "12345", "Pedram", "Razzaghi", "73453112234"),
                924437L, new Customer(924437L, "omid.n@gmail.com", "12345", "Omid", "Naaderi", "34235345233")
        ));

        public Customer customer(Long id) {
            return customers.get(id);
        }

        public int count() {
            return customers.size();
        }

        public void addCustomer(Customer customer) {
            Objects.requireNonNull(customer);
            Objects.requireNonNull(customer.getId());
            customers.put(customer.getId(), customer);
        }

        public Optional<Customer> findCustomerByUsername(String username) {
            return customers.values().stream()
                            .filter(customer -> customer.getUsername().equals(username))
                            .findFirst();
        }

        public boolean exist(String username, String nationalId) {
            return customers.values().stream().anyMatch(customer -> customer.getNationalId().equals(nationalId) || customer.getUsername().equals(username));
        }
    }

    public static class PlaneDataSource implements Serializable {
        private final Map<Long, Plane> planes;

        public PlaneDataSource() {
            planes = new HashMap<>(Map.of(
                    600L, new Plane(600L, 120, "Fokker", getAirlineTransportationCompanies().company(500L)),
                    601L, new Plane(601L, 320, "Fokker F70", getAirlineTransportationCompanies().company(500L)),
                    602L, new Plane(602L, 331, "Airbus E10", getAirlineTransportationCompanies().company(501L)),
                    603L, new Plane(603L, 480, "Boeing 740", getAirlineTransportationCompanies().company(501L)),
                    604L, new Plane(604L, 120, "Fokker", getAirlineTransportationCompanies().company(502L)),
                    605L, new Plane(605L, 510, "Airbus L769", getAirlineTransportationCompanies().company(503L)),
                    606L, new Plane(606L, 639, "Boeing S980", getAirlineTransportationCompanies().company(504L)),
                    607L, new Plane(607L, 420, "Airbus A350", getAirlineTransportationCompanies().company(505L)),
                    608L, new Plane(608L, 545, "Airbus A340", getAirlineTransportationCompanies().company(506L))
            ));
            planes.forEach((key, val) -> val.getTransportationCompany().addVehicle(val));
        }

        public Plane plane(Long id) {
            return planes.get(id);
        }
    }

    public static class PlaneSeatDataSource implements Serializable {
        private final Map<Long, PlaneSeat> seats;
        private int lastId = 800;

        public PlaneSeatDataSource() {
            seats = new HashMap<>();
            getSeatingSections().sections().forEach(this::addSeatsToSection);
        }

        private void addSeatsToSection(SeatingSection seatingSection) {
            for (int i = 1; i <= seatingSection.getCapacity(); i++) {
                PlaneSeat seat = new PlaneSeat((long) lastId, Long.toString(seatingSection.getId() + i), seatingSection);
                seatingSection.addSeat(seat);
                seats.put((long) lastId++, seat);
            }
        }

        public PlaneSeat seat(Long id) {
            return seats.get(id);
        }

    }

    public static class SeatingSectionDataSource implements Serializable {
        private final Map<Long, SeatingSection> seatingSections = new HashMap<>();

        public SeatingSectionDataSource() {
            seatingSections.put(700L, new EconomyClassSeatingSection(700L, 120, getPlanes().plane(600L)));
            seatingSections.put(701L, new EconomyClassSeatingSection(701L, 250, getPlanes().plane(601L)));
            seatingSections.put(702L, new BusinessClassSeatingSection(702L, 70, getPlanes().plane(601L)));
            seatingSections.put(703L, new EconomyClassSeatingSection(703L, 270, getPlanes().plane(602L)));
            seatingSections.put(704L, new BusinessClassSeatingSection(704L, 61, getPlanes().plane(602L)));
            seatingSections.put(705L, new EconomyClassSeatingSection(705L, 480, getPlanes().plane(603L)));
            seatingSections.put(706L, new EconomyClassSeatingSection(706L, 120, getPlanes().plane(604L)));
            seatingSections.put(707L, new EconomyClassSeatingSection(707L, 510, getPlanes().plane(605L)));
            seatingSections.put(708L, new EconomyClassSeatingSection(708L, 639, getPlanes().plane(606L)));
            seatingSections.put(709L, new EconomyClassSeatingSection(709L, 260, getPlanes().plane(607L)));
            seatingSections.put(710L, new FirstClassSeatingSection(710L, 130, getPlanes().plane(607L)));
            seatingSections.put(714L, new BusinessClassSeatingSection(714L, 30, getPlanes().plane(607L)));
            seatingSections.put(711L, new EconomyClassSeatingSection(711L, 300, getPlanes().plane(608L)));
            seatingSections.put(712L, new FirstClassSeatingSection(712L, 200, getPlanes().plane(608L)));
            seatingSections.put(713L, new BusinessClassSeatingSection(713L, 45, getPlanes().plane(608L)));
            seatingSections.values().forEach(v -> v.getPlane().addSection(v));
        }

        public SeatingSection seatingSection(Long id) {
            return seatingSections.get(id);
        }

        public int count() {
            return seatingSections.size();
        }

        public Collection<SeatingSection> sections() {
            return seatingSections.values();
        }
    }

    public static class SeatingSectionPrivilegeDataSource implements Serializable {
        private final Map<Long, SeatingSectionPrivilege> privileges;

        private long lastId = 900;

        public SeatingSectionPrivilegeDataSource() {
            privileges = new HashMap<>();
            addPrivilegesToSection(getAirlineTransportations().transportation(1001L).getVehicle().getSeatingSections());
            addPrivilegesToSection(getAirlineTransportations().transportation(1002L).getVehicle().getSeatingSections());
        }

        private void addPrivilegesToSection(Set<SeatingSection> seatingSections) {
            for (SeatingSection section : seatingSections) {
                if (section instanceof FirstClassSeatingSection) {
                    SeatingSectionPrivilege privilege = new SeatingSectionPrivilege(lastId, "Study Room", section);
                    addPrivilegeToSection(section, privilege);
                    privilege = new SeatingSectionPrivilege(lastId, "Massage", section);
                    addPrivilegeToSection(section, privilege);
                }
            }
        }

        private void addPrivilegeToSection(SeatingSection section, SeatingSectionPrivilege privilege) {
            privileges.put(lastId++, privilege);
            section.addSectionPrivilege(privilege);
        }

        public SeatingSectionPrivilege privilege(Long id) {
            return privileges.get(id);
        }
    }

    public static class TerminalDataSource implements Serializable {

        private final Map<Long, Terminal> terminals = new HashMap<>(Map.of(
                400L, new Terminal(400L, "Mehraabad Airport", getCities().city(334L)),
                401L, new Terminal(401L, "Imam Khomeini Airport", getCities().city(334L)),
                402L, new Terminal(402L, "Shiraaz Airport", getCities().city(335L)),
                403L, new Terminal(403L, "London Airport", getCities().city(336L)),
                404L, new Terminal(404L, "Paris Airport", getCities().city(337L)),
                405L, new Terminal(405L, "Istanbul Airport", getCities().city(338L)),
                406L, new Terminal(406L, "Ankara Airport", getCities().city(339L)),
                407L, new Terminal(407L, "Anadolu Airport", getCities().city(340L))
        ));

        public Terminal terminal(Long id) {
            return terminals.get(id);
        }

        public Collection<Terminal> terminals() {
            return terminals.values();
        }
    }

    public static class PlaneTicketDataSource implements Serializable {

        private final Map<Long, FlightTicket> tickets;

        public PlaneTicketDataSource() {
            this.tickets = new HashMap<>();
        }

        public void addTicket(FlightTicket ticket) {
            tickets.put(ticket.getId(), ticket);
        }

        public int count() {
            return tickets.size();
        }

        public Collection<FlightTicket> getAllTickets() {
            return tickets.values();
        }

        public Set<FlightTicket> getUsersTicketsByUsername(String username) {
            return tickets.values().stream()
                          .filter(ticket -> ticket.getCustomer().getUsername().equals(username))
                          .collect(Collectors.toSet());

        }
    }

    public static CityDataSource getCities() {
        return cities;
    }

    public static CountryDataSource getCountries() {
        return countries;
    }

    public static AirlineTransportationCompanyDatasource getAirlineTransportationCompanies() {
        return airlineTransportationCompanies;
    }

    public static PlaneDataSource getPlanes() {
        return planes;
    }

    public static AirlineTransportationDataSource getAirlineTransportations() {
        return airlineTransportations;
    }

    public static PlaneSeatDataSource getSeats() {
        return seats;
    }

    public static SeatingSectionDataSource getSeatingSections() {
        return seatingSections;
    }

    public static CustomerDataSource getCustomers() {
        return customers;
    }

    public static SeatingSectionPrivilegeDataSource getSeatingSectionPrivileges() {
        return seatingSectionPrivileges;
    }

    public static TerminalDataSource getTerminals() {
        return terminals;
    }

    public static PlaneTicketDataSource getTickets() {
        return tickets;
    }

    public static void save() {
        Path path = Paths.get("app.data");
        System.out.println(path.toAbsolutePath());
        try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(getCities());
            objectOutputStream.writeObject(getCountries());
            objectOutputStream.writeObject(getTerminals());
            objectOutputStream.writeObject(getCustomers());
            objectOutputStream.writeObject(getPlanes());
            objectOutputStream.writeObject(getSeatingSections());
            objectOutputStream.writeObject(getSeatingSectionPrivileges());
            objectOutputStream.writeObject(getSeats());
            objectOutputStream.writeObject(getAirlineTransportationCompanies());
            objectOutputStream.writeObject(getAirlineTransportations());
            objectOutputStream.writeObject(getTickets());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDate() {
        Path path = Paths.get("app.data");

        try (InputStream inputStream = Files.newInputStream(path)) {
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            cities = (CityDataSource) ois.readObject();
            countries = (CountryDataSource) ois.readObject();
            terminals = (TerminalDataSource) ois.readObject();
            customers = (CustomerDataSource) ois.readObject();
            planes = (PlaneDataSource) ois.readObject();
            seatingSections = (SeatingSectionDataSource) ois.readObject();
            seatingSectionPrivileges = (SeatingSectionPrivilegeDataSource) ois.readObject();
            seats = (PlaneSeatDataSource) ois.readObject();
            airlineTransportationCompanies = (AirlineTransportationCompanyDatasource) ois.readObject();
            airlineTransportations = (AirlineTransportationDataSource) ois.readObject();
            tickets = (PlaneTicketDataSource) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            init();
        }
    }
}
