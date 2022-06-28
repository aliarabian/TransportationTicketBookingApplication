package com.platform.repository.ticket;

import com.platform.business.model.booking.BookingOrder;
import persistence.data.storage.memory.DuplicateItemException;

import java.util.Set;

public interface BookingOrderDao {
    void persist(BookingOrder ticket) throws DuplicateItemException;


    Set<BookingOrder> getAllOrders();
    Set<BookingOrder> getUsersBookingOrdersByUsername(String username);
}
