package com.platform.controllers.users;


import com.platform.ApiResponseEntity;
import com.platform.ResourceCreationDetails;
import com.platform.business.service.booking.dto.FlightTicketDto;
import com.platform.business.service.tickets.TicketsService;
import com.platform.business.service.users.RegistrationService;
import com.platform.business.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "users", produces = APPLICATION_JSON_VALUE)
public class UsersController {
    private final RegistrationService userRegistrationService;
    private final TicketsService ticketsService;

    public UsersController(RegistrationService usersService, TicketsService ticketsService) {
        this.userRegistrationService = usersService;
        this.ticketsService = ticketsService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseEntity<ResourceCreationDetails>> registerUser(@RequestBody @Valid UserDto userDto) {
        User user = userRegistrationService.register(userDto);
        System.out.println(user);
        ResourceCreationDetails resourceCreationDetails = new ResourceCreationDetails("/users/" + user.getId());
        return ResponseEntity.created(resourceCreationDetails.getLocation())
                .body(new ApiResponseEntity<>(resourceCreationDetails));
    }


    @GetMapping("{userId}/flight-bookings")
    public ResponseEntity<ApiResponseEntity<List<FlightTicketDto>>> usersFlightBookings(@PathVariable String userId, Authentication authentication) {
        String username = ((Jwt) authentication.getPrincipal()).getSubject();
        return ResponseEntity.ok(new ApiResponseEntity<>(ticketsService.findTicketsByUsername(username)));
    }

}
