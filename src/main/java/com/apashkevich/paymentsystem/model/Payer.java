package com.apashkevich.paymentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payer")
public class Payer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "payer_login", length = 30, nullable = false, unique = true)
    private String login;

    @Column(name = "payer_account")
    private BigDecimal account;

    @OneToMany(mappedBy = "payer")
    private List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setPayer(this);
    }



}
