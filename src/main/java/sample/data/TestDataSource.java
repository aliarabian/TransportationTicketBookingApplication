package sample.data;

import com.transportation.airline.tickets.booking.entity.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class TestDataSource implements Serializable {
    public static CityDataSource cities;
    public static CountryDataSource countries;
    public static AirlineTransportationCompanyDatasource airlineTransportationCompanies;
    public static PlaneDataSource planes;
    public static AirlineTransportationDataSource airlineTransportations;
    public static PlaneSeatDataSource seats;
    public static SeatingSectionDataSource seatingSections;
    public static CustomerDataSource customers;
    public static SeatingSectionPrivilegeDataSource seatingSectionPrivileges;
    public static TerminalDataSource terminals;

    static {
        if (Files.exists(Paths.get("app.data"))) {
            loadDate();
        } else {
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
        }

    }

    private static class AirlineTransportationCompanyDatasource implements Serializable {

        private final Map<Long, TransportationCompany<AirlineTransportation, Plane>> airlines = new HashMap<>(Map.of(
                500L, new TransportationCompany<>(500L, "Homa", cities.city(334L)),
                501L, new TransportationCompany<>(501L, "Iran Air", cities.city(334L)),
                502L, new TransportationCompany<>(502L, "Hafez", cities.city(335L)),
                503L, new TransportationCompany<>(500L, "Emirates", cities.city(337L)),
                504L, new TransportationCompany<>(504L, "Turkish Airlines", cities.city(338L)),
                505L, new TransportationCompany<>(505L, "Ankara Ocaklari", cities.city(339L)),
                506L, new TransportationCompany<>(505L, "Anadolu Airlines", cities.city(340L))
        ));

        public TransportationCompany<AirlineTransportation, Plane> company(Long id) {
            return airlines.get(id);
        }
    }

    public static class AirlineTransportationDataSource implements Serializable {
        private final Map<Long, AirlineTransportation> transportations;

        public AirlineTransportationDataSource() {
            transportations = new HashMap<>();
            transportations.put(1001L, new AirlineTransportation(1001L,
                    terminals.terminal(400L),
                    terminals.terminal(402L),
                    ZonedDateTime.of(2022, 6, 12,
                            5, 10, 0, 0,
                            ZoneId.of("Asia/Tehran"))
                    , planes.plane(600L))
            );

            transportations.put(1002L, new AirlineTransportation(1002L,
                    terminals.terminal(401L),
                    terminals.terminal(402L),
                    ZonedDateTime.of(2022, 6, 27,
                            6, 45, 0, 0,
                            ZoneId.of("Asia/Tehran"))
                    , planes.plane(607L))
            );
        }

        public AirlineTransportation transportation(Long id) {
            return transportations.get(id);
        }
    }

    public static class CityDataSource implements Serializable {
        private final Map<Long, City> cities;

        public CityDataSource() {
            cities = new HashMap<>(Map.of(
                    333L, new City(333L, "Zanjan", countries.country("IR")),
                    334L, new City(334L, "Tehran", countries.country("IR")),
                    335L, new City(335L, "Shiraaz", countries.country("IR")),
                    336L, new City(336L, "London", countries.country("UK")),
                    337L, new City(337L, "Paris", countries.country("FR")),
                    338L, new City(338L, "Istanbul", countries.country("TR")),
                    339L, new City(339L, "Ankara", countries.country("TR")),
                    340L, new City(340L, "Anadolu", countries.country("TR"))
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
    }

    public static class CustomerDataSource implements Serializable {
        private final Map<Long, Customer> customers = new HashMap<>(Map.of(
                924427L, new Customer(924427L, "ali.arabian@gmail.com", "Ali", "Arabian", "4986350641"),
                924429L, new Customer(924429L, "Faraanak.ghm@gmail.com", "Faraanak", "Gholaami", "44886450641"),
                924430L, new Customer(924430L, "laleh.malekian@gmail.com", "Laleh", "Malekian", "00651298772"),
                924431L, new Customer(924431L, "alihosseini@gmail.com", "Ali", "Hosseini", "98752136987"),
                924432L, new Customer(924432L, "elhamkebriya@gmail.com", "Elham", "Kebriyayi", "45925300140"),
                924433L, new Customer(924433L, "abbasi.nazli@gmail.com", "Nazli", "Abbasi", "01215480006"),
                924434L, new Customer(924434L, "homi.k@gmail.com", "Homayoun", "kazemi", "23423220887"),
                924435L, new Customer(924435L, "kaboli.m@gmail.com", "Mahdi", "Kaboli", "87799544552"),
                924436L, new Customer(924436L, "pedramrazaghi@gmail.com", "Pedram", "Razzaghi", "73453112234"),
                924437L, new Customer(924437L, "omid.n@gmail.com", "Omid", "Naaderi", "34235345233")
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
    }

    public static class PlaneDataSource implements Serializable {
        private final Map<Long, Plane> planes;

        public PlaneDataSource() {
            planes = new HashMap<>(Map.of(
                    600L, new Plane(600L, 120, "Fokker", airlineTransportationCompanies.company(500L)),
                    601L, new Plane(601L, 320, "Fokker F70", airlineTransportationCompanies.company(500L)),
                    602L, new Plane(602L, 331, "Airbus E10", airlineTransportationCompanies.company(501L)),
                    603L, new Plane(603L, 480, "Boeing 740", airlineTransportationCompanies.company(501L)),
                    604L, new Plane(604L, 120, "Fokker", airlineTransportationCompanies.company(502L)),
                    605L, new Plane(605L, 510, "Airbus L769", airlineTransportationCompanies.company(503L)),
                    606L, new Plane(606L, 639, "Boeing S980", airlineTransportationCompanies.company(504L)),
                    607L, new Plane(607L, 420, "Airbus A350", airlineTransportationCompanies.company(505L)),
                    608L, new Plane(608L, 545, "Airbus A340", airlineTransportationCompanies.company(506L))
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
            seatingSections.sections().forEach(this::addSeatsToSection);
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
            seatingSections.put(700L, new EconomyClassSeatingSection(700L, 120, planes.plane(600L)));
            seatingSections.put(701L, new EconomyClassSeatingSection(701L, 250, planes.plane(601L)));
            seatingSections.put(702L, new BusinessClassSeatingSection(702L, 70, planes.plane(601L)));
            seatingSections.put(703L, new EconomyClassSeatingSection(703L, 270, planes.plane(602L)));
            seatingSections.put(704L, new BusinessClassSeatingSection(704L, 61, planes.plane(602L)));
            seatingSections.put(705L, new EconomyClassSeatingSection(705L, 480, planes.plane(603L)));
            seatingSections.put(706L, new EconomyClassSeatingSection(706L, 120, planes.plane(604L)));
            seatingSections.put(707L, new EconomyClassSeatingSection(707L, 510, planes.plane(605L)));
            seatingSections.put(708L, new EconomyClassSeatingSection(708L, 639, planes.plane(606L)));
            seatingSections.put(709L, new EconomyClassSeatingSection(709L, 390, planes.plane(607L)));
            seatingSections.put(710L, new FirstClassSeatingSection(710L, 30, planes.plane(607L)));
            seatingSections.put(711L, new EconomyClassSeatingSection(711L, 300, planes.plane(608L)));
            seatingSections.put(712L, new FirstClassSeatingSection(712L, 200, planes.plane(608L)));
            seatingSections.put(713L, new BusinessClassSeatingSection(713L, 45, planes.plane(608L)));
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
            addPrivilegesToSection(airlineTransportations.transportation(1001L).getVehicle().getSeatingSections());
            addPrivilegesToSection(airlineTransportations.transportation(1002L).getVehicle().getSeatingSections());
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
                400L, new Terminal(400L, "Mehraabad Airport", cities.city(334L)),
                401L, new Terminal(401L, "Imam Khomeini Airport", cities.city(334L)),
                402L, new Terminal(402L, "Shiraaz Airport", cities.city(335L)),
                403L, new Terminal(403L, "London Airport", cities.city(336L)),
                404L, new Terminal(404L, "Paris Airport", cities.city(337L)),
                405L, new Terminal(405L, "Istanbul Airport", cities.city(338L)),
                406L, new Terminal(406L, "Ankara Airport", cities.city(339L)),
                407L, new Terminal(407L, "Anadolu Airport", cities.city(340L))
        ));

        public Terminal terminal(Long id) {
            return terminals.get(id);
        }

    }

    public static void save() {
        Path path = Paths.get("app.data");
        System.out.println(path.toAbsolutePath());
        try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(cities);
            objectOutputStream.writeObject(countries);
            objectOutputStream.writeObject(terminals);
            objectOutputStream.writeObject(customers);
            objectOutputStream.writeObject(planes);
            objectOutputStream.writeObject(seatingSections);
            objectOutputStream.writeObject(seatingSectionPrivileges);
            objectOutputStream.writeObject(seats);
            objectOutputStream.writeObject(airlineTransportationCompanies);
            objectOutputStream.writeObject(airlineTransportations);
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}