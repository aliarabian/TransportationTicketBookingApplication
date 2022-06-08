package com.platform.security;

import com.platform.business.model.Customer;
import com.platform.repository.customer.CustomerDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppUserDetailsService implements UserDetailsService {

    private final CustomerDao customerDao;

    public AppUserDetailsService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerDao.findCustomerByUsername(username)
                                       .orElseThrow(() -> new UsernameNotFoundException("Wrong Credentials"));

        return User.builder().username(username)
                   .password(customer.getPassword())
                   .authorities(List.of(new SimpleGrantedAuthority("USER"),
                           new SimpleGrantedAuthority("ADMIN")))
                   .disabled(false)
                   .accountLocked(false)
                   .accountExpired(false)
                   .accountLocked(false)
                   .build();
    }
}
