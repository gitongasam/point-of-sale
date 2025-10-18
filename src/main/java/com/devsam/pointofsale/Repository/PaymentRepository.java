package com.devsam.pointofsale.Repository;


import com.devsam.pointofsale.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    boolean existsByMpesaTransId(String mpesaTransId);
}

