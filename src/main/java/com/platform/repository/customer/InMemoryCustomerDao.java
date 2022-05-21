package com.platform.repository.customer;

import com.platform.business.model.Customer;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Optional;

@Repository
public class InMemoryCustomerDao implements CustomerDao {

    public InMemoryCustomerDao() {
    }


    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return Optional.ofNullable(TransportationBookingSystemImMemoryDataSource.getCustomers().customer(id));

    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return TransportationBookingSystemImMemoryDataSource.getCustomers()
                                                            .findCustomerByUsername(username);
    }
}
