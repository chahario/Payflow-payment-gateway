package com.payflow.paymentgateway.dto;

import com.payflow.paymentgateway.entity.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResponse(UUID id,
                              Payment.PaymentStatus status
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getStatus()
        );
    }
}
