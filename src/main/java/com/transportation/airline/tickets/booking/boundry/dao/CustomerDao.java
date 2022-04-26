package com.transportation.airline.tickets.booking.boundry.dao;

import com.transportation.airline.tickets.booking.entity.Customer;

import java.util.Optional;

public interface CustomerDao {
    Optional<Customer> findCustomerById(Long id);
}
