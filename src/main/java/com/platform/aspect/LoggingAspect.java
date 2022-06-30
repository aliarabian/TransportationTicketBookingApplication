package com.platform.aspect;

import com.platform.business.booking.dto.FlightTicketDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class LoggingAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.platform.business.booking.*Service.*(..))")
    private void bookingServicePointcut() {
    }

    @Before("bookingServicePointcut()")
    public void logBookingServiceMethodInputsAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        StringBuilder sb = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        sb.append(signature.toLongString());
        sb.append(", input arguments = [");
        for (var arg : args) {
            sb.append(arg).append(", ");
        }
        sb.append("]");
        LOGGER.info("Booking Method {}", sb);
    }

    @AfterReturning(value = "bookingServicePointcut()", returning = "tickets")
    public void logBookedTickets(Set<FlightTicketDto> tickets) {
        LOGGER.info("Booked Tickets: [ {} ]", tickets);
    }

    @AfterThrowing(value = "bookingServicePointcut() || execution(* com.platform.controllers.*.*(..)) || execution(* com.platform.business.booking.FlightTicketBookingController.*(..))", throwing = "failureException")
    public void logBookingFailureExceptionAdvice(JoinPoint joinPoint, Throwable failureException) {
        Signature signature = joinPoint.getSignature();
        StringBuilder sb = new StringBuilder();
        sb.append(signature.toLongString());
        LOGGER.info("Booking Failed, Method: {},  Exception Message: {}",
                sb,
                failureException.getMessage()
        );
    }
}
