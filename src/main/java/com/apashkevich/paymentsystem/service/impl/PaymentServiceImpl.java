package com.apashkevich.paymentsystem.service.impl;

import com.apashkevich.paymentsystem.model.Payee;
import com.apashkevich.paymentsystem.model.Payer;
import com.apashkevich.paymentsystem.model.Payment;
import com.apashkevich.paymentsystem.repository.PaymentRepository;
import com.apashkevich.paymentsystem.rest.dto.PaymentDto;
import com.apashkevich.paymentsystem.service.PayeeService;
import com.apashkevich.paymentsystem.service.PayerService;
import com.apashkevich.paymentsystem.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private PayerService payerService;
    private PayeeService payeeService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PayerService payerService, PayeeService payeeService) {
        this.paymentRepository = paymentRepository;
        this.payerService = payerService;
        this.payeeService = payeeService;
    }

    @Override
    @Transactional
    public PaymentDto createPayment(PaymentDto paymentDto) {

        Payer payerByLogin = payerService.getPayerByLogin(paymentDto.getPayerLogin());
        Payee payeeByLogin = payeeService.getPayeeByLogin(paymentDto.getPayeeLogin());


        Payment payment = Payment.builder().amount(paymentDto.getAmount())
                .payee(payeeByLogin)
                .payer(payerByLogin)
                .build();

        //payeeByLogin.addPayment(payment);
       // payerByLogin.addPayment(payment);

        Payment save = paymentRepository.save(payment);

        return  PaymentDto.builder().id(save.getId())
                .amount(payment.getAmount())
                .payeeLogin(save.getPayee().getLogin())
                .payerLogin(save.getPayer().getLogin())
                .build();
    }
}
