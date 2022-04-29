package com.platform.repository.customer;

import com.platform.enitity.Customer;

import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> findCustomerById(Long id);
}
