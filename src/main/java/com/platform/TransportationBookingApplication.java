package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TransportationBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransportationBookingApplication.class, args);
    }
}
