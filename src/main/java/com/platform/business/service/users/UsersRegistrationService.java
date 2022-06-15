package com.platform.business.service.users;

import com.platform.business.model.Customer;
import com.platform.business.model.User;
import com.platform.controllers.users.UserDto;
import com.platform.repository.customer.CustomerDao;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersRegistrationService implements RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final CustomerDao customerDao;

    public UsersRegistrationService(PasswordEncoder passwordEncoder, CustomerDao customerDao) {
        this.passwordEncoder = passwordEncoder;
        this.customerDao = customerDao;
    }

    @Override
    public User register(UserDto userDto) {

        boolean exists = customerDao.exists(userDto.getUsername(), userDto.getNationalId());
        if (exists) {
            throw new CustomerExistsException("Customer Exists");
        }
        Customer customer = new Customer(Long.parseLong(userDto.getNationalId()), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
                userDto.getFirstName(), userDto.getLastName(), userDto.getNationalId());
        customerDao.insert(customer);
        return customer;
    }
}
