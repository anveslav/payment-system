package com.apashkevich.paymentsystem.repository;

import com.apashkevich.paymentsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.payer.id = ?1")
    BigDecimal getSumByPayer(Long payerId);
}
