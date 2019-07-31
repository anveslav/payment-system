package com.apashkevich.paymentsystem.rest.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private UUID id;

    private BigDecimal amount;

    @NotNull
    private String payerLogin;

    @NotNull
    private String payeeLogin;
}
