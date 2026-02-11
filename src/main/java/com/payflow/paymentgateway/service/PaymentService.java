package com.payflow.paymentgateway.service;

import com.payflow.paymentgateway.dto.CreatePaymentRequest;
import com.payflow.paymentgateway.entity.Payment;
import com.payflow.paymentgateway.repository.PaymentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
public class PaymentService {

    //“I mark injected dependencies as final to enforce immutability, constructor injection, and thread-safe service design.”
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Payment createPayment(
            CreatePaymentRequest request,
            String idempotencyKey
    ){
        // checking  if the request was already processed.
        Optional<Payment> existing =
                repository.findByIdempotencyKey(idempotencyKey);
        if(existing.isPresent()){
            return existing.get(); // retry logic
        }
        // first time payment
        Payment payment = new Payment(
                request.amount(),
                request.currency(),
                request.userId(),
                idempotencyKey);
        // to handle concurrent request
        // two requests with same Idempotency key arrive at same time.
        //Check first → try insert → let DB decide → recover gracefully
        //Databases are the best concurrency control mechanisms we have.
        try{
            return repository.saveAndFlush(payment); // instead of save() -- as no lazy flushes.
        } catch (DataIntegrityViolationException ex){
            // race condition - 2 threads, so graceful  recovery
            return repository
                    .findByIdempotencyKey(idempotencyKey)
                    .orElseThrow();
        }
    }
}