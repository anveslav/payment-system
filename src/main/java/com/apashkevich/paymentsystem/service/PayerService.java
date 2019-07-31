package com.apashkevich.paymentsystem.service;

import com.apashkevich.paymentsystem.model.Payer;

public interface PayerService {

    Payer savePayer(Payer payer);

    Payer getPayerByLogin(String login);

}
