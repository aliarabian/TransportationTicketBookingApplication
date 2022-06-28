package com.platform.business.service.booking;

import com.platform.business.exception.BadRequestException;
import com.platform.business.exception.CustomerNotFoundException;
import com.platform.business.exception.TransportationNotFoundException;
import com.platform.business.mapper.Mapper;
import com.platform.business.model.*;
import com.platform.business.model.booking.BookingOrder;
import com.platform.business.model.booking.FlightTicket;
import com.platform.business.model.booking.Passport;
import com.platform.business.model.transportation.Flight;
import com.platform.business.model.transportation.FlightPassenger;
import com.platform.business.model.transportation.PlaneSeat;
import com.platform.business.model.transportation.SeatingSectionPrivilege;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.booking.dto.request.FlightPassengerDto;
import com.platform.business.service.booking.dto.request.PlaneBookingPassengerDetail;
import com.platform.business.service.booking.dto.request.PlaneTicketBookingRequest;
import com.platform.business.service.booking.exception.BookingException;
import com.platform.business.service.booking.exception.PassengerExistsException;
import com.platform.business.service.booking.exception.TransportationHappenedBeforeException;
import com.platform.repository.country.CountryDao;
import com.platform.repository.customer.CustomerDao;
import com.platform.repository.ticket.FlightTicketDao;
import com.platform.repository.transportation.FlightsDao;
import org.springframework.stereotype.Service;
import persistence.data.storage.memory.DuplicateItemException;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class FlightTicketBookingService implements BookingService {
    private final FlightsDao flightsDao;
    private final FlightTicketDao ticketDao;
    private final CountryDao countryDao;
    private final CustomerDao customerDao;
    private final Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper;

    public FlightTicketBookingService(FlightsDao flightsDao, FlightTicketDao ticketDao,
                                      CountryDao countryDao, CustomerDao customerDao,
                                      Mapper<FlightTicket, FlightTicketDto> ticketDtoMapper) {

        this.flightsDao = flightsDao;
        this.ticketDao = ticketDao;
        this.countryDao = countryDao;
        this.customerDao = customerDao;
        this.ticketDtoMapper = ticketDtoMapper;
    }


    @Override
    public Set<FlightTicketDto> getAllBookings() {
        return ticketDao.getAllTickets().stream()
                        .map(ticketDtoMapper::toDto)
                        .collect(Collectors.toSet());
    }

    @Override
    public BookingOrder bookTicketsWithRequestedSeats(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException {
        nullCheck(request, username, flightId);
        if (request.getSeatIds().size() != request.getPassengersBookingDetails().size()) {
            throw new BadRequestException("Number of seats and passengers doesn't match.");
        }
        Flight flight = getFlight(flightId);
        if (flight.getDeparturesAt().isBefore(OffsetDateTime.now(flight.getDeparturesAt().getOffset()))) {
            throw new TransportationHappenedBeforeException("Flight Time has passed.");
        }
        Customer customer = getCustomer(username);


        List<PlaneSeat> planeSeats = flight.bookSeats(request.getSeatingSectionId(), request.getSeatIds());
        List<PlaneBookingPassengerDetail> passengersBookingDetails = new ArrayList<>(request.getPassengersBookingDetails());
        Set<FlightTicket> tickets = new HashSet<>();
        for (int i = 0; i < planeSeats.size(); i++) {
            PlaneSeat seat = planeSeats.get(i);
            PlaneBookingPassengerDetail planeBookingPassengerDetail = passengersBookingDetails.get(i);
            FlightTicket flightTicket = createFlightTicket(flight, customer, planeBookingPassengerDetail, seat);
            tickets.add(flightTicket);
        }
        return new BookingOrder(ThreadLocalRandom.current().nextLong(2000L, 999_999_999),
                tickets, customer);
    }


    @Override
    public Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest request, String username, Long flightId) throws BookingException, NullPointerException {
        nullCheck(request, username, flightId);

        Flight flight = getFlight(flightId);
        if (flight.getDeparturesAt().isBefore(OffsetDateTime.now(flight.getDeparturesAt().getOffset()))) {
            throw new TransportationHappenedBeforeException("Flight Time has passed.");
        }
        Customer customer = getCustomer(username);

        return bookTickets(request, flight, customer);
    }


    private Set<FlightTicketDto> bookTickets(PlaneTicketBookingRequest req, Flight flight, Customer customer) {
        Set<FlightTicketDto> flightTicketDtos = new TreeSet<>(Comparator.comparing(FlightTicketDto::getPassportNO));
        Set<FlightTicket> tickets = new HashSet<>();
        try {
            for (PlaneBookingPassengerDetail bookingDetail : req.getPassengersBookingDetails()) {
                PlaneSeat seat = flight.bookSeat(req.getSeatingSectionId());
                tickets.add(createFlightTicket(flight, customer, bookingDetail, seat));
            }
            ticketDao.persist(tickets);
            flight.addNewBookings(tickets);
            customer.addTickets(tickets);
            tickets.forEach(ticket -> flightTicketDtos.add(ticketDtoMapper.toDto(ticket)));
        } catch (DuplicateItemException e) {
            throw new PassengerExistsException("Passenger Has Already Booked A Ticket For This Flight!");
        }
        return flightTicketDtos;
    }

    private FlightTicket createFlightTicket(Flight flight, Customer customer, PlaneBookingPassengerDetail bookingDetail, PlaneSeat seat) {
        Set<SeatingSectionPrivilege> privileges = seat.getSection().retrievePrivilegesById(bookingDetail.getSeatingSectionPrivilegeIds());
        return new FlightTicket(
                flight, privileges, toPassenger(bookingDetail.getPassenger()),
                seat, customer);
    }

    private FlightPassenger toPassenger(FlightPassengerDto passengerDto) {
        Country country = countryDao.findByCountryCode(passengerDto.getPassportIssuingCountryCode())
                                    .orElseThrow();
        Passport passport = new Passport(Long.valueOf(passengerDto.getPassportNO()), passengerDto.getPassportNO(), passengerDto.getPassportExpirationDate(), country);
        return new FlightPassenger(Long.valueOf(passengerDto.getNationalIdNO()), passengerDto.getFirstName(), passengerDto.getLastName()
                , passengerDto.getNationalIdNO(), passengerDto.getBirthdate(), passport);
    }

    private Customer getCustomer(String username) {
        return customerDao.findCustomerByUsername(username)
                          .orElseThrow(() -> new CustomerNotFoundException("Customer Doesn't Exists"));
    }

    private Flight getFlight(Long flightId) {
        return flightsDao.findTransportationById(flightId)
                         .orElseThrow(() -> new TransportationNotFoundException("Wrong Transportation Number"));
    }

    private void nullCheck(PlaneTicketBookingRequest request, String username, Long flightId) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(username);
        Objects.requireNonNull(flightId);
    }
}
