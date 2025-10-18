package com.devsam.pointofsale.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "mpesa_trans_id", unique = true, nullable = false)
    private String mpesaTransId;

    @Column(name = "payer_msisdn", nullable = false)
    private String payerMsisdn;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status; // RECEIVED, VERIFIED, FAILED

    @Column(columnDefinition = "TEXT")
    private String rawPayload;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
