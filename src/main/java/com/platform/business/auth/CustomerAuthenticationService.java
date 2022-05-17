package com.platform.business.auth;

import com.platform.business.auth.exception.AuthenticationFailedException;
import com.platform.model.Customer;
import com.platform.repository.customer.CustomerDao;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerAuthenticationService implements AuthenticationService {

    private final CustomerDao customerDao;

    public CustomerAuthenticationService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationFailedException {
        Objects.requireNonNull(request);
        Customer customer = customerDao.findCustomerByUsername(request.getUsername())
                                       .orElseThrow(() -> new AuthenticationFailedException("Wrong Credentials"));
        if (passwordMatches(request.getPassword(), customer.getPassword())) {
            return new AuthenticationResponse(UUID.randomUUID().toString().replaceAll("-", ""), customer.getUsername(), customer.getId());
        }

        throw new AuthenticationFailedException("Wrong Credentials");
    }

    private boolean passwordMatches(String requestPassword, String password) {
        return password.equals(requestPassword);
    }
}
