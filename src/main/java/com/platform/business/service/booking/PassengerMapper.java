package com.platform.business.service.booking;

import com.platform.business.enitity.PlanePassenger;
import com.platform.business.service.booking.dto.PlanePassengerDto;

public interface PassengerMapper {
    PlanePassenger mapToPlanePassenger(PlanePassengerDto passengerDto);
    PlanePassengerDto mapToPlanePassengerDto(PlanePassenger passenger);
}
