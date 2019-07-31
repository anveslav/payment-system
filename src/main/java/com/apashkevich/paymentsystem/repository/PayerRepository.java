package com.apashkevich.paymentsystem.repository;

import com.apashkevich.paymentsystem.model.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerRepository extends JpaRepository<Payer, Long> {

    Payer findByLogin(String login);

}
