package com.payflow.paymentgateway.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "idempotencyKey")
        })
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false,length = 3)
    private String currency;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false,unique = true)
    private String idempotencyKey;

    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    // Non- args constructors
    protected Payment() {}

    // Public constructors(used by application code)
    public Payment(BigDecimal amount, String currency, String userId, String idempotencyKey) {
//        this.id = UUID.randomUUID(); // uniqueness enforced by application not database(important for distributed System)
        this.amount = amount;
        this.currency = currency;
        this.userId = userId;
        this.idempotencyKey = idempotencyKey;
        this.status = PaymentStatus.INITIATED;
        this.createdAt = Instant.now();
    }
    // getters
    public UUID getId() {
        return id;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getUserId() {
        return userId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }




public enum PaymentStatus {
    INITIATED,
    SUCCESS,
    FAILED,}
}

