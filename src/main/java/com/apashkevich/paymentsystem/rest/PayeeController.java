package com.apashkevich.paymentsystem.rest;

import com.apashkevich.paymentsystem.model.Payee;
import com.apashkevich.paymentsystem.model.Payer;
import com.apashkevich.paymentsystem.service.PayeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayeeController {

    private PayeeService payeeService;

    public PayeeController(PayeeService payeeService) {
        this.payeeService = payeeService;
    }

    @PostMapping("/payee")
    public ResponseEntity<Payee> createPayer(@RequestBody Payee payee){
        return ResponseEntity.ok(payeeService.savePayee(payee));

    }
}
