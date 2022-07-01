package com.platform.business;

import com.platform.business.booking.BookingOrderDao;
import com.platform.business.booking.entity.BookingOrder;
import com.platform.business.booking.entity.FlightTicket;
import com.platform.business.booking.entity.OrderStatus;
import com.platform.business.booking.entity.Ticket;
import com.platform.business.model.transportation.PlaneSeat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@ApplicationScope
public class ResetSeatStateService {
    @Value("${seat.hold.timeout}")
    private int timeout;
    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(8);
    private final BookingOrderDao orderDao;

    public ResetSeatStateService(BookingOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void scheduleResetOnTimeout(BookingOrder order) {
        List<PlaneSeat> seats = order.getTickets().stream()
                                     .map(Ticket::getSeat)
                                     .collect(Collectors.toList());
        Runnable resetTask = () -> {
            if (order.getStatus().equals(OrderStatus.FULFILLED)) {
                return;
            }
            BookingOrder orderEntity = orderDao.getOrderByIdAndUsername(order.getId(), order.getCustomer().getUsername());
            for (FlightTicket ticket : orderEntity.getTickets()) {
                ticket.getSeat().free();
                ticket.getSeat().getSection().incrementAvailableSeats();
            }
            orderDao.cancel(order);
        };

        scheduledExecutor.schedule(resetTask, timeout, TimeUnit.MINUTES);
    }
}
