package com.platform.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
//@Order(1)
public class LoggingAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.platform.business.service.booking.*Service.*(..))")
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

    @AfterThrowing(value = "bookingServicePointcut() || execution(* com.platform.controllers.*.*(..))", throwing = "failureException")
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
