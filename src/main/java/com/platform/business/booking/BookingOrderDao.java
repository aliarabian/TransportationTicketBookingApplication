package com.platform.business.booking;

import com.platform.business.booking.entity.BookingOrder;
import persistence.data.storage.memory.DuplicateItemException;

import java.util.Set;

public interface BookingOrderDao {
    void persist(BookingOrder ticket) throws DuplicateItemException;


    Set<BookingOrder> getAllOrders();
    Set<BookingOrder> getUsersBookingOrdersByUsername(String username);
}
