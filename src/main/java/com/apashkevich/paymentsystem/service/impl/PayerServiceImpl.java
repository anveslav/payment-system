package com.apashkevich.paymentsystem.service.impl;

import com.apashkevich.paymentsystem.model.Payer;
import com.apashkevich.paymentsystem.repository.PayerRepository;
import com.apashkevich.paymentsystem.service.PayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayerServiceImpl implements PayerService {

    PayerRepository payerRepository;

    public PayerServiceImpl(PayerRepository payerRepository) {
        this.payerRepository = payerRepository;
    }

    @Override
    @Transactional
    public Payer savePayer(Payer payer) {
        return payerRepository.save(payer);
    }

    @Override
    @Transactional(readOnly = true)
    public Payer getPayerByLogin(String login) {
        return payerRepository.findByLogin(login);
    }
}
