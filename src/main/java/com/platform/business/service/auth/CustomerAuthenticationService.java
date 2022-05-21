package com.platform.business.service.auth;

import com.platform.business.model.Customer;
import com.platform.repository.customer.CustomerDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class CustomerAuthenticationService implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDao customerDao;

    public CustomerAuthenticationService(AuthenticationManager authenticationManager, CustomerDao customerDao) {
        this.authenticationManager = authenticationManager;
        this.customerDao = customerDao;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        Objects.requireNonNull(request);
        Customer customer = customerDao.findCustomerByUsername(request.getUsername())
                                       .orElseThrow(() -> new UsernameNotFoundException("Wrong Credentials"));

        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        return new AuthenticationResponse(UUID.randomUUID().toString().replaceAll("-", ""), customer.getUsername(), customer.getId());

    }


}