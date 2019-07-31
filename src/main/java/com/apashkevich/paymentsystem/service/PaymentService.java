package com.apashkevich.paymentsystem.service;

import com.apashkevich.paymentsystem.rest.dto.PaymentDto;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto payment);

    List<PaymentDto> getAllPayments();

    BigDecimal getPaymentSumByPayer(String payerLogin);
}
