package com.devsam.pointofsale.Repository;

import com.devsam.pointofsale.Entity.PaymentReconciliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentReconciliationRepository extends JpaRepository<PaymentReconciliation, UUID> {}

