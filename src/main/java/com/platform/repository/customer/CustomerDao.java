package com.platform.repository.customer;

import com.platform.model.Customer;

import java.util.Optional;


public interface CustomerDao {
    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByUsername(String username);
}
