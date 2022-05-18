package com.platform.controllers.users;


import com.platform.ApiResponseEntity;
import com.platform.ResourceCreationDetails;
import com.platform.business.users.RegistrationService;
import com.platform.model.FlightTicket;
import com.platform.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "users", produces = APPLICATION_JSON_VALUE)
public class UsersController {
    private final RegistrationService userRegistrationService;

    public UsersController(RegistrationService usersService) {
        this.userRegistrationService = usersService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseEntity<ResourceCreationDetails>> registerUser(@RequestBody UserDto userDto) {
        User user = userRegistrationService.register(userDto);
        ResourceCreationDetails resourceCreationDetails = new ResourceCreationDetails("/users/" + user.getId());
        return ResponseEntity.created(resourceCreationDetails.getLocation())
                             .body(new ApiResponseEntity<>(resourceCreationDetails));
    }


    @GetMapping("{userId}/flight-bookings")
    public ResponseEntity<ApiResponseEntity<Set<FlightTicket>>> usersFlightBookings() {
        return ResponseEntity.ok(new ApiResponseEntity<>(Set.of()));
    }

}
