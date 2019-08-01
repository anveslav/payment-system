package com.apashkevich.paymentsystem;

import com.apashkevich.paymentsystem.model.Payee;
import com.apashkevich.paymentsystem.model.Payer;
import com.apashkevich.paymentsystem.repository.PayeeRepository;
import com.apashkevich.paymentsystem.repository.PayerRepository;
import com.apashkevich.paymentsystem.repository.PaymentRepository;
import com.apashkevich.paymentsystem.rest.dto.PaymentDto;
import com.apashkevich.paymentsystem.service.PaymentService;
import com.apashkevich.paymentsystem.utils.JsonMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentSystemApplication.class)
@AutoConfigureMockMvc
public class PaymentApiIntegrationTest {

    public static final String PAYEE_LOGIN = "vitya50";
    public static final String PAYER_LOGIN = "andrey25";
    public static final String PAYER_NOT_EXIST_LOGIN = "andrey44";
    public static final BigDecimal PAYER_INITIAL_BALANCE = BigDecimal.valueOf(100);
    public static final BigDecimal PAYEE_INITIAL_BALANCE = BigDecimal.valueOf(0);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PayerRepository payerRepository;

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;


    private MockMvc mockMvc;

    @Before
    @Transactional
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        Payer payer = Payer.builder().login(PAYER_LOGIN).account(PAYER_INITIAL_BALANCE).build();
        Payee payee = Payee.builder().login(PAYEE_LOGIN).account(PAYEE_INITIAL_BALANCE).build();
        payerRepository.save(payer);
        payeeRepository.save(payee);
    }

    @After
    @Transactional
    public void cleanUp() throws Exception {
        paymentRepository.deleteAll();
        payerRepository.deleteAll();
        payeeRepository.deleteAll();
    }


    @Test
    @Transactional
    public void shouldReturnResultOkIfPaymentIsSuccessful() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .payer(PAYER_LOGIN)
                .payee(PAYEE_LOGIN)
                .amount(BigDecimal.valueOf(50.0)).build();

        this.mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.paymentDtoToJson(paymentDto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.payer").value(PAYER_LOGIN))
                .andExpect(jsonPath("$.payee").value(PAYEE_LOGIN));

        Payer payer = payerRepository.findByLogin(PAYER_LOGIN);
        Payee payee = payeeRepository.findByLogin(PAYEE_LOGIN);

        BigDecimal expectedPayerBalance = BigDecimal.valueOf(50.0).setScale(3, BigDecimal.ROUND_CEILING);
        BigDecimal expectedPayeeBalance = BigDecimal.valueOf(50.0).setScale(3, BigDecimal.ROUND_CEILING);

        //check balance changes for payer and payee
        Assert.assertEquals(expectedPayerBalance, payer.getAccount().setScale(3, BigDecimal.ROUND_CEILING));
        Assert.assertEquals(expectedPayeeBalance, payee.getAccount().setScale(3, BigDecimal.ROUND_CEILING));
        Assert.assertEquals(paymentRepository.findAll().size(), 1);
    }

    @Test
    @Transactional
    public void shouldReturnResultNotFoundIfPayerDoesntExist() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .payer(PAYER_NOT_EXIST_LOGIN)
                .payee(PAYEE_LOGIN)
                .amount(BigDecimal.valueOf(50.0)).build();

        this.mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.paymentDtoToJson(paymentDto)))
                .andExpect(status().isNotFound());

        Assert.assertEquals(paymentRepository.findAll().size(), 0);
    }

    @Test
    @Transactional
    public void shouldReturnResultBadRequestIfPayerBalanceIsNotEnoughForPayment() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .payer(PAYER_LOGIN)
                .payee(PAYEE_LOGIN)
                .amount(BigDecimal.valueOf(1050.0)).build();

        this.mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonMapper.paymentDtoToJson(paymentDto)))
                .andExpect(status().isBadRequest());

        Payer payer = payerRepository.findByLogin(PAYER_LOGIN);
        Payee payee = payeeRepository.findByLogin(PAYEE_LOGIN);

        BigDecimal expectedPayerBalance = BigDecimal.valueOf(100.0).setScale(3, BigDecimal.ROUND_CEILING);
        BigDecimal expectedPayeeBalance = BigDecimal.valueOf(0.0).setScale(3, BigDecimal.ROUND_CEILING);

        //check balance didn't change for payer and payee
        Assert.assertEquals(expectedPayerBalance, payer.getAccount().setScale(3, BigDecimal.ROUND_CEILING));
        Assert.assertEquals(expectedPayeeBalance, payee.getAccount().setScale(3, BigDecimal.ROUND_CEILING));

        Assert.assertEquals(paymentRepository.findAll().size(), 0);
    }

    @Test
    @Transactional
    public void shouldReturnListOfPayments() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .payer(PAYER_LOGIN)
                .payee(PAYEE_LOGIN)
                .amount(BigDecimal.valueOf(50.0)).build();


        paymentService.createPayment(paymentDto);

        this.mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].amount").value(50.0));

    }

    @Test
    @Transactional
    public void shouldReturnSumForPayer() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .payer(PAYER_LOGIN)
                .payee(PAYEE_LOGIN)
                .amount(BigDecimal.valueOf(50.0)).build();


        paymentService.createPayment(paymentDto);
        paymentService.createPayment(paymentDto);

        this.mockMvc.perform(get("/payments/amount/{login}", PAYER_LOGIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100));

    }

    @Test
    @Transactional
    public void shouldReturnPayerNotFoundWhenGettingSum() throws Exception {

        PaymentDto paymentDto = PaymentDto
                .builder()
                .payer(PAYER_LOGIN)
                .payee(PAYEE_LOGIN)
                .amount(BigDecimal.valueOf(50.0)).build();


        paymentService.createPayment(paymentDto);
        paymentService.createPayment(paymentDto);

        this.mockMvc.perform(get("/payments/amount/{login}", PAYER_NOT_EXIST_LOGIN))
                .andExpect(status().isNotFound());
    }
}
