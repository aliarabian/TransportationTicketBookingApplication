package com.platform.business.booking;

import com.platform.business.booking.dto.PlanePassengerDto;
import com.platform.enitity.PlanePassenger;

public interface PassengerMapper {
    PlanePassenger mapToPlanePassenger(PlanePassengerDto passengerDto);
    PlanePassengerDto mapToPlanePassengerDto(PlanePassenger passenger);
}
