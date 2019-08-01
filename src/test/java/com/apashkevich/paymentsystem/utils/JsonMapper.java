package com.apashkevich.paymentsystem.utils;

import com.apashkevich.paymentsystem.rest.dto.PaymentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {

    public static String PaymentDtoToJson(PaymentDto paymentDto){
        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return mapper.writeValueAsString(paymentDto);
        } catch (JsonProcessingException e) {
            log.error("Couldn't write JSON");
            throw new IllegalStateException("Couldn't write JSON", e);
        }
    }
}
