package com.platform.repository.ticket;

import com.platform.business.model.booking.BookingOrder;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.DuplicateItemException;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Set;

@Repository
public class FlightBookingOrderDao implements BookingOrderDao {
    @Override
    public void persist(BookingOrder order) throws DuplicateItemException {
        TransportationBookingSystemImMemoryDataSource.getOrders().addOrder(order);

    }

    @Override
    public Set<BookingOrder> getAllOrders() {
        return null;
    }

    @Override
    public Set<BookingOrder> getUsersBookingOrdersByUsername(String username) {
        return null;
    }
}
