package com.payflow.paymentgateway.controller;
import com.payflow.paymentgateway.dto.CreatePaymentRequest;
import com.payflow.paymentgateway.dto.PaymentResponse;
import com.payflow.paymentgateway.entity.Payment;
import com.payflow.paymentgateway.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreatePaymentRequest request) {
        Payment payment =
                paymentService.createPayment(request,idempotencyKey);
        return ResponseEntity.ok(PaymentResponse.from(payment));
    }
}

