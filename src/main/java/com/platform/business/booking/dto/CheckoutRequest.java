package com.platform.business.booking.dto;

import javax.validation.constraints.NotNull;

public class CheckoutRequest {
    @NotNull
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
