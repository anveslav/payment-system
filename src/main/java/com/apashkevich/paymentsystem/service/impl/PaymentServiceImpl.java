package com.apashkevich.paymentsystem.service.impl;

import com.apashkevich.paymentsystem.exception.InsufficientFundsException;
import com.apashkevich.paymentsystem.exception.PayeeNotFoundException;
import com.apashkevich.paymentsystem.exception.PayerNotFoundException;
import com.apashkevich.paymentsystem.model.Payee;
import com.apashkevich.paymentsystem.model.Payer;
import com.apashkevich.paymentsystem.model.Payment;
import com.apashkevich.paymentsystem.repository.PaymentRepository;
import com.apashkevich.paymentsystem.rest.dto.PaymentDto;
import com.apashkevich.paymentsystem.service.PayeeService;
import com.apashkevich.paymentsystem.service.PayerService;
import com.apashkevich.paymentsystem.service.PaymentService;
import com.apashkevich.paymentsystem.utils.PaymentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

        Payer payer = payerService.getPayerByLogin(paymentDto.getPayer());
        Payee payee = payeeService.getPayeeByLogin(paymentDto.getPayee());

        if (payer == null) {
            log.error("Couldn't find payer");
            throw new PayerNotFoundException("Couldn't find payer with login:" + paymentDto.getPayer());
        } else if (payee == null) {
            log.error("Couldn't find payee");
            throw new PayeeNotFoundException("Couldn't find payee with login:" + paymentDto.getPayee());
        }

        Payment payment = Payment.builder().amount(paymentDto.getAmount())
                .payee(payee)
                .payer(payer)
                .build();

        BigDecimal payerBalance = payer.getAccount();

        if (payerBalance.compareTo(payment.getAmount()) == -1) {
            log.error("Balance should be more or equals to payment amount");
            throw new InsufficientFundsException("Insufficient funds in the payer's account");
        }

        BigDecimal newPayeeAmount = payee.getAccount().add(payment.getAmount());
        BigDecimal newPayerAmount = payer.getAccount().subtract(payment.getAmount());

        payee.setAccount(newPayeeAmount);
        payer.setAccount(newPayerAmount);

        payerService.savePayer(payer);
        payeeService.savePayee(payee);

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentMapper.toPaymentDto(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(PaymentMapper::toPaymentDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getPaymentSumByPayer(String payerLogin) {
        Payer payer = payerService.getPayerByLogin(payerLogin);
        if (payer == null){
            log.error("Couldn't find payer");
            throw new PayerNotFoundException("Couldn't find payer with login:" + payerLogin);
        }
        return paymentRepository.getSumByPayer(payer.getId());
    }
}
