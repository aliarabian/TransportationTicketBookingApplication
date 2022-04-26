package com.transportation.airline.tickets.booking.boundry.dao;

import com.transportation.airline.tickets.booking.entity.Customer;
import sample.data.TestDataSource;

import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    public CustomerDaoImpl() {
    }


    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return Optional.ofNullable(TestDataSource.customers.customer(id));

    }
}
