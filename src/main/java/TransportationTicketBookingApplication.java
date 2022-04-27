import com.transportation.airline.tickets.booking.boundry.dto.PlaneBookingPassengerDetail;
import com.transportation.airline.tickets.booking.boundry.dto.PlanePassengerDto;
import com.transportation.airline.tickets.booking.boundry.dto.PlaneTicketBookingRequest;
import sample.data.TestDataSource;
import ui.cli.CommandHandler;
import ui.cli.CommandHandlerFactory;

import java.time.ZonedDateTime;
import java.util.Set;

public class TransportationTicketBookingApplication {
    public static void main(String[] args) {
        System.out.println(TestDataSource.tickets.count());
        if (args.length == 0) {
            System.err.println("No Commands Provided!");
            System.exit(-1);
        }
        CommandHandlerFactory.commandHandler(args[0])
                             .ifPresentOrElse(CommandHandler::handle,
                                     () -> System.err.println("Command Not Supported"));

        System.out.println(TestDataSource.tickets.count());
        TestDataSource.save();
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

