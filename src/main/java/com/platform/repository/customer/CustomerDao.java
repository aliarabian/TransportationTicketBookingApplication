package com.platform.repository.customer;

import com.platform.business.model.Customer;

import java.util.Optional;


public interface CustomerDao {
    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByUsername(String username);

    boolean exists(String username, String nationalId);

    void insert(Customer customer);
}
