package com.apashkevich.paymentsystem.repository;

import com.apashkevich.paymentsystem.model.Payee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, Long> {

    Payee findByLogin(String login);
}
