package com.platform.business.service.users;

import com.platform.controllers.users.UserDto;
import com.platform.business.model.User;

public interface RegistrationService {
    User register(UserDto userDto);
}
