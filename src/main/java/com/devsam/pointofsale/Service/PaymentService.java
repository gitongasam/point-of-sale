package com.devsam.pointofsale.Service;

import com.devsam.pointofsale.Entity.Order;
import com.devsam.pointofsale.Entity.OrderStatus;
import com.devsam.pointofsale.Entity.Payment;
import com.devsam.pointofsale.Entity.PaymentReconciliation;
import com.devsam.pointofsale.Repository.OrderRepository;
import com.devsam.pointofsale.Repository.PaymentReconciliationRepository;
import com.devsam.pointofsale.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentReconciliationRepository reconRepository;
    private final PlatformTransactionManager txManager;

    public void processMpesaCallback(Map<String, Object> payload) {

        // Extract fields from M-Pesa callback
        String mpesaTransId = (String) payload.get("TransID");
        BigDecimal amount = new BigDecimal(String.valueOf(payload.get("TransAmount")));
        String msisdn = (String) payload.get("MSISDN");

        // BillRefNumber contains the Order ID (UUID)
        String billRef = (String) payload.get("BillRefNumber");
        UUID orderId = UUID.fromString(billRef);

        TransactionTemplate tx = new TransactionTemplate(txManager);
        tx.execute(status -> {

            // Idempotency: only process each transaction once
            if (paymentRepository.existsByMpesaTransId(mpesaTransId)) return null;

            // Find order by UUID
            Optional<Order> optOrder = orderRepository.findById(orderId);
            if (optOrder.isPresent()) {
                Order order = optOrder.get();

                // Match exact amount
                if (order.getAmount().compareTo(amount) == 0) {
                    // Create payment record
                    Payment payment = new Payment();
                    payment.setOrder(order);
                    payment.setAmount(amount);
                    payment.setMpesaTransId(mpesaTransId);
                    payment.setPayerMsisdn(msisdn);
                    payment.setStatus("VERIFIED");
                    payment.setRawPayload(payload.toString());
                    paymentRepository.saveAndFlush(payment);

                    // Update order status
                    order.setStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                } else {
                    logReconciliation(payload, "Amount mismatch");
                }
            } else {
                logReconciliation(payload, "Order not found");
            }

            return null;
        });
    }

    private void logReconciliation(Map<String, Object> payload, String reason) {
        PaymentReconciliation recon = new PaymentReconciliation();
        recon.setMpesaTransId((String) payload.get("TransID"));
        recon.setRawPayload(payload.toString());
        recon.setResolution(reason);
        reconRepository.save(recon);
    }
}
