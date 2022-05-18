package com.platform.business.users;

import com.platform.controllers.users.UserDto;
import com.platform.model.User;

public interface RegistrationService {
    User register(UserDto userDto);
}
