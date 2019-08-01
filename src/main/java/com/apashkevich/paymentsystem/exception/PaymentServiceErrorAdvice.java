package com.apashkevich.paymentsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PaymentServiceErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({PayeeNotFoundException.class, PayerNotFoundException.class})
    public void handle() {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InsufficientFundsException.class})
    public void handle(InsufficientFundsException e) {}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class})
    public void handle(RuntimeException e) {}

}
