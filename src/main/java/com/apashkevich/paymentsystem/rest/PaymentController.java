package com.apashkevich.paymentsystem.rest;

import com.apashkevich.paymentsystem.model.Payment;
import com.apashkevich.paymentsystem.rest.dto.PaymentDto;
import com.apashkevich.paymentsystem.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
