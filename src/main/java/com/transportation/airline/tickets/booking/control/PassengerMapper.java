package com.transportation.airline.tickets.booking.control;

import com.transportation.airline.tickets.booking.boundry.dto.PlanePassengerDto;
import com.transportation.airline.tickets.booking.entity.PlanePassenger;

public interface PassengerMapper {
    PlanePassenger mapToPlanePassenger(PlanePassengerDto passengerDto);
    PlanePassengerDto mapToPlanePassengerDto(PlanePassenger passenger);
}
