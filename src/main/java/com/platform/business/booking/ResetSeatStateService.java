package com.platform.business.booking;

import com.platform.business.booking.entity.BookingOrder;
import com.platform.business.booking.entity.FlightTicket;
import com.platform.business.booking.entity.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@ApplicationScope
public class ResetSeatStateService {
    @Value("${seat.hold.timeout}")
    private int timeout;
    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(8);
    private final BookingOrderDao orderDao;
    private final ApplicationEventPublisher eventPublisher;

    public ResetSeatStateService(BookingOrderDao orderDao, ApplicationEventPublisher eventPublisher) {
        this.orderDao = orderDao;
        this.eventPublisher = eventPublisher;
    }

    public void scheduleResetOnTimeout(BookingOrder order) {
        Runnable resetTask = () -> {
            BookingOrder orderEntity = orderDao.getOrderByIdAndUsername(order.getId(), order.getCustomer().getUsername());
            if (orderEntity.getStatus().equals(OrderStatus.FULFILLED)) {
                return;
            }
            for (FlightTicket ticket : orderEntity.getTickets()) {
                ticket.getSeat().free();
                ticket.getSeat().getSection().incrementAvailableSeats();
            }
            order.updateStatus(OrderStatus.CANCELLED);
            orderDao.cancel(order);
            eventPublisher.publishEvent(new SeatStateChangedEvent(order.getTickets()
                                                                       .stream()
                                                                       .findFirst()
                                                                       .get()
                                                                       .getSeat()
                                                                       .getSection()));
        };

        scheduledExecutor.schedule(resetTask, timeout, TimeUnit.MINUTES);
    }
}
