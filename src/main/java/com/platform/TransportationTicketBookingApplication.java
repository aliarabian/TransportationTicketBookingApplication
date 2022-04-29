package com.platform;

import com.platform.business.booking.dto.PlaneBookingPassengerDetail;
import com.platform.business.booking.dto.PlanePassengerDto;
import com.platform.business.booking.dto.PlaneTicketBookingRequest;
import com.platform.ui.cli.CommandHandler;
import com.platform.ui.cli.CommandHandlerFactory;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.time.ZonedDateTime;
import java.util.Set;

import static java.lang.System.*;

public class TransportationTicketBookingApplication {
    public static void main(String[] args) {
        out.println(TransportationBookingSystemImMemoryDataSource.getTickets().count());
        if (args.length == 0) {
            err.println("No Commands Provided!");
            exit(-1);
        }
        CommandHandlerFactory.commandHandler(args[0])
                             .ifPresentOrElse(CommandHandler::handle, () -> err.println("Command Not Supported"));

        out.println(TransportationBookingSystemImMemoryDataSource.getTickets().count());
        TransportationBookingSystemImMemoryDataSource.save();
    }

    private static PlaneTicketBookingRequest initializePlaneTicketBookingRequest(PlaneBookingPassengerDetail... passengersDetails) {
        PlaneTicketBookingRequest request = new PlaneTicketBookingRequest();
        request.setCustomerId(924427L);
        request.setTransportationId(1002L);
        request.setSeatingSectionId(710L);
        request.setPassengersBookingDetails(Set.of(passengersDetails));
        return request;
    }

    private static PlaneBookingPassengerDetail getPlaneBookingPassengerDetail(PlanePassengerDto passengerDto) {
        PlaneBookingPassengerDetail ticketBookingDetail = new PlaneBookingPassengerDetail();
        ticketBookingDetail.setPassenger(passengerDto);
        ticketBookingDetail.setSeatingSectionPrivilegeIds(Set.of(901L, 903234L));
        return ticketBookingDetail;
    }

    private static PlanePassengerDto generatePassengerDto(String firstName, String lastName, String nationalId,
                                                          String passportNO, String passportIssuingCountryCode, ZonedDateTime passportExpirationDate) {
        PlanePassengerDto passengerDto = new PlanePassengerDto();
        passengerDto.setFirstName(firstName);
        passengerDto.setLastName(lastName);
        passengerDto.setNationalIdNO(nationalId);
        passengerDto.setPassportNO(passportNO);
        passengerDto.setPassportIssuingCountryCode(passportIssuingCountryCode);
        passengerDto.setPassportExpirationDate(passportExpirationDate);
        return passengerDto;
    }
}

