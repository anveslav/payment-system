package com.apashkevich.paymentsystem.service;

import com.apashkevich.paymentsystem.model.Payee;

public interface PayeeService {

    Payee savePayee(Payee payee);

    Payee getPayeeByLogin(String login);
}
