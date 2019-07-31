package com.apashkevich.paymentsystem.service;

import com.apashkevich.paymentsystem.rest.dto.PaymentDto;

public interface PaymentService {

     PaymentDto createPayment(PaymentDto payment);
}
