package com.apashkevich.paymentsystem.rest;

import com.apashkevich.paymentsystem.model.Payer;
import com.apashkevich.paymentsystem.model.Payment;
import com.apashkevich.paymentsystem.service.PayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayerController {

    private PayerService payerService;

    public PayerController(PayerService payerService) {
        this.payerService = payerService;
    }

    @PostMapping("/payer")
    public ResponseEntity<Payer> createPayer(@RequestBody Payer payer){
        return ResponseEntity.ok(payerService.savePayer(payer));

    }
}
