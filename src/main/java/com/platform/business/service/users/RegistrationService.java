package com.platform.business.service.users;

import com.platform.business.model.User;
import com.platform.controllers.users.UserDto;

public interface RegistrationService {
    User register(UserDto userDto);
}
