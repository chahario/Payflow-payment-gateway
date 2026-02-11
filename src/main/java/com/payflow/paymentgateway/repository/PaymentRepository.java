package com.payflow.paymentgateway.repository;

import com.payflow.paymentgateway.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
// JpaRepository -> prebuilt interface, that knows to do CRUD, so we use it instead of writing queries.
// Spring will generate the implementation at runtime.

// JpaRepository<Payment, UUID>  -> means - this repo manages Payment Object, Primary key type is UUID.
// we get save(payment)
//findById(id)
//findAll()
//deleteById(id)
//existsById(id)   without writing them.

// at runtime -> Spring see this interface, create a proxy class , that proxy uses hibernate , hibernate generates SQL.
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
}

// Optional --> means there might be a value, or there might be nothing.
// why Optional -> cuz null causes crashes, optional force you to check.

// custom method -> findByIdempotencyKey, just a naming convention -> spring read this name as: find + By + Idempotency Key
// meaning -> Select * from payments where idempotencyKey = value.
//why this method -> cuz    idempotency logic needs to answer have i already seen this request before?
// and the safe place to answer is Database.