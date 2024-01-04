package com.challange.api.tranferAndBalanceConsult.controller;

import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountDTO;
import com.challange.api.tranferAndBalanceConsult.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks private AccountController controller;
    @Mock private AccountService service;
    @Test
    void testShouldTransferAmount() {

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        RequestCheckingAccountDTO requestDTO = RequestCheckingAccountDTO.builder()
                .idBank("1")
                .transferAmount(200.00)
                .checkingAccountFrom(checkingAccountFrom)
                .checkingAccountTo(checkingAccountTo)
                .build();

        Assertions.assertDoesNotThrow(
                () -> controller.checkingAccountTransfer(requestDTO));
    }

    @Test
    void testShouldConsultBalance() {

        RequestBalanceConsultDTO requestDTO = RequestBalanceConsultDTO.builder()
                .issuer("0001")
                .number("1234")
                .build();

        Assertions.assertDoesNotThrow(
                () -> controller.consultBalance(requestDTO));
    }

    @Test
    void testShouldSchedule() {

        Assertions.assertDoesNotThrow(
                () -> controller.schedulingBacenErrors());
    }
}
