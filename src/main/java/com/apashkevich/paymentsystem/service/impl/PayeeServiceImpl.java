package com.apashkevich.paymentsystem.service.impl;

import com.apashkevich.paymentsystem.model.Payee;
import com.apashkevich.paymentsystem.repository.PayeeRepository;
import com.apashkevich.paymentsystem.service.PayeeService;
import org.springframework.stereotype.Service;

@Service
public class PayeeServiceImpl implements PayeeService {

    private PayeeRepository payeeRepository;

    public PayeeServiceImpl(PayeeRepository payeeRepository) {
        this.payeeRepository = payeeRepository;
    }

    @Override
    public Payee savePayee(Payee payee) {
        return payeeRepository.save(payee);
    }

    @Override
    public Payee getPayeeByLogin(String login) {
        return payeeRepository.findByLogin(login);
    }
}
