package com.apashkevich.paymentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payee")
public class Payee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "payee_login", length = 30, nullable = false, unique = true)
    private String login;

    @Column(name = "payee_account")
    private BigDecimal account;

    @OneToMany(mappedBy = "payee")
    private List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setPayee(this);
    }
}
