package com.platform.business.service.auth;

import com.platform.business.model.Customer;
import com.platform.repository.customer.CustomerDao;
import com.platform.security.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerAuthenticationService implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDao customerDao;
    private final JwtUtil jwtUtil;

    public CustomerAuthenticationService(AuthenticationManager authenticationManager, CustomerDao customerDao, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerDao = customerDao;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        Objects.requireNonNull(request);
        Customer customer = customerDao.findCustomerByUsername(request.getUsername())
                                       .orElseThrow(() -> new UsernameNotFoundException("Wrong Credentials"));

        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        String token = jwtUtil.creatJwtFromUserDetails((UserDetails) authenticated.getPrincipal());

        return new AuthenticationResponse(token, customer.getUsername(), customer.getId());

    }


}