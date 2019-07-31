package com.apashkevich.paymentsystem.rest;

import com.apashkevich.paymentsystem.rest.dto.PaymentDto;
import com.apashkevich.paymentsystem.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto payment){
        return ResponseEntity.ok(paymentService.createPayment(payment));

    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDto>> getAllPayments(){
        return ResponseEntity.ok(paymentService.getAllPayments());

    }

    @GetMapping("/payments/amount/{login}")
    public ResponseEntity<BigDecimal> getPaymentSum(@PathVariable String login){
        return ResponseEntity.ok(paymentService.getPaymentSumByPayer(login));
    }

}
