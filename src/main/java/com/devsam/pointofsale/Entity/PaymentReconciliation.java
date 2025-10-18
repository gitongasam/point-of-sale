package com.devsam.pointofsale.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_reconciliation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReconciliation {

    @Id
    @GeneratedValue
    private UUID id;

    private String mpesaTransId;

    @Column(columnDefinition = "TEXT")
    private String rawPayload;

    private String resolution;

    @Column(nullable = false)
    private LocalDateTime attemptedAt = LocalDateTime.now();
}
