package com.apashkevich.paymentsystem.exception;

public class PayeeNotFoundException extends IllegalStateException {

    public PayeeNotFoundException(String message) {
        super(message);
    }
}
