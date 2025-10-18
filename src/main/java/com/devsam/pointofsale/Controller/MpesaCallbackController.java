package com.devsam.pointofsale.Controller;

import com.devsam.pointofsale.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mpesa")
@RequiredArgsConstructor
public class MpesaCallbackController {

    private final PaymentService paymentService;

    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody Map<String, Object> payload) {
        try {
            paymentService.processMpesaCallback(payload);
            return ResponseEntity.ok("{\"ResultCode\":0,\"ResultDesc\":\"Accepted\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("{\"ResultCode\":1,\"ResultDesc\":\"Processing error\"}");
        }
    }
}

