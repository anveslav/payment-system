package com.apashkevich.paymentsystem.exception;

public class PayerNotFoundException extends IllegalStateException {

    public PayerNotFoundException(String message) {
        super(message);
    }
}
