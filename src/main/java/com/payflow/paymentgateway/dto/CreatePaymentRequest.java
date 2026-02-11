package com.payflow.paymentgateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotNull @Positive BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String userId) {}

// A record is an immutable data carrier, introduced to avoid boilerplate.

// equivalent to
//class CreatePaymentRequest{
//    private final BigDecimal amount;
//    private final String currency;
//    private final String userId;
//}

// Why immutable?
// cuz request data should not change
// prevent accidental modification
// Thread-safe by default

// Validation Annotation
// @NotNull - field must present

// where does validation happen?
// when the request hits the controller
// Spring reads annotations
// validate input
// if invalid -> return 400 Bad request
// your code runs never --> Huge safety


