package com.apashkevich.paymentsystem.utils;

import com.apashkevich.paymentsystem.model.Payment;
import com.apashkevich.paymentsystem.rest.dto.PaymentDto;

public class PaymentMapper {

    public static PaymentDto toPaymentDto(Payment payment){
        return PaymentDto.builder().id(payment.getId())
                .amount(payment.getAmount())
                .payee(payment.getPayee().getLogin())
                .payer(payment.getPayer().getLogin())
                .createdDate(payment.getCreatedDate())
                .build();
    }
}
