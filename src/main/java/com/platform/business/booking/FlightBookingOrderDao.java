package com.platform.business.booking;

import com.platform.business.booking.entity.BookingOrder;
import com.platform.business.booking.entity.OrderStatus;
import com.platform.business.booking.exception.BookingException;
import org.springframework.stereotype.Repository;
import persistence.data.storage.memory.DuplicateItemException;
import persistence.data.storage.memory.TransportationBookingSystemImMemoryDataSource;

import java.util.Optional;
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
        return TransportationBookingSystemImMemoryDataSource.getOrders().getUsersBookingOrdersByUsername(username);
    }

    @Override
    public void cancel(BookingOrder order) {
        TransportationBookingSystemImMemoryDataSource.getOrders().updateOrder(order.updateStatus(OrderStatus.CANCELLED));
    }

    @Override
    public BookingOrder getOrderByIdAndUsername(Long orderId, String username) {
        Optional<BookingOrder> bookingOrder = TransportationBookingSystemImMemoryDataSource.getOrders().getOrderByIdAndUsername(orderId, username);
        return bookingOrder.orElseThrow(() -> new BookingException("Order Not Found"));
    }
}
