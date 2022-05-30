package com.platform.business.service.users;

import com.platform.business.model.Customer;
import com.platform.controllers.users.UserDto;
import com.platform.business.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersRegistrationService implements RegistrationService {
    private final PasswordEncoder passwordEncoder;

    public UsersRegistrationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserDto userDto) {
        // TODO Implement Completely
        return new Customer(Long.parseLong(userDto.getNationalId()), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
                userDto.getFirstName(), userDto.getLastName(), userDto.getNationalId());
    }
}
