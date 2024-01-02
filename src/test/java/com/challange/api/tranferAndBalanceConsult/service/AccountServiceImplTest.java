package com.challange.api.tranferAndBalanceConsult.service;

import com.challange.api.tranferAndBalanceConsult.client.APICadastroClient;
import com.challange.api.tranferAndBalanceConsult.exception.NotFoundException;
import com.challange.api.tranferAndBalanceConsult.mapper.AccountMapper;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import com.challange.api.tranferAndBalanceConsult.model.dto.APICadastroDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.tranferAndBalanceConsult.model.entity.TransferAndBalanceConsultEntity;
import com.challange.api.tranferAndBalanceConsult.repository.TransferAndBalanceRepository;
import com.challange.api.tranferAndBalanceConsult.service.impl.AccountServiceImpl;
import com.challange.api.tranferAndBalanceConsult.validator.CheckingAccountValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private APICadastroClient cadastroClient;

    @Mock
    private TransferAndBalanceRepository transferAndBalanceRepository;

    @Mock
    private CheckingAccountValidator checkingAccountValidator;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl service;

    @Test
    void testCheckingAccountTransfer() throws Exception {
        APICadastroDTO cadastroDTO = APICadastroDTO.builder()
                .name("Client_1")
                .build();

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        RequestCheckingAccountTransferDTO requestDTO = RequestCheckingAccountTransferDTO.builder()
                .checkingAccountTo(checkingAccountTo)
                .checkingAccountFrom(checkingAccountFrom)
                .transferAmount(200.00)
                .idBank("1")
                .build();

        Optional<TransferAndBalanceConsultEntity> transferEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_1")
                .isActive(true)
                .date("01-01-2023")
                .balance(200.00)
                .dailyLimitUsed(0.00)
                .build());

        Mockito.when(cadastroClient.requestToAPICadastro("1")).thenReturn(cadastroDTO);
        Mockito.when(transferAndBalanceRepository.findByName(cadastroDTO.getName())).thenReturn(transferEntity);
        Mockito.when(transferAndBalanceRepository.findByIssuerAndNumber(requestDTO.getCheckingAccountTo().getIssuer(), requestDTO.getCheckingAccountFrom().getNumber())).thenReturn(transferEntity);

        Assertions.assertDoesNotThrow(
                () -> service.checkingAccountTransfer(requestDTO));
    }

    @Test
    void testCheckingAccountTransferError() throws Exception {
        APICadastroDTO cadastroDTO = APICadastroDTO.builder()
                .name("Client_1")
                .build();

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0001")
                .number("1234")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        RequestCheckingAccountTransferDTO requestDTO = RequestCheckingAccountTransferDTO.builder()
                .checkingAccountTo(checkingAccountTo)
                .checkingAccountFrom(checkingAccountFrom)
                .transferAmount(200.00)
                .idBank("1")
                .build();

        Optional<TransferAndBalanceConsultEntity> transferEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_1")
                .isActive(true)
                .date("01-01-2023")
                .balance(200.00)
                .dailyLimitUsed(0.00)
                .build());

        Mockito.when(cadastroClient.requestToAPICadastro("1")).thenReturn(cadastroDTO);
        Mockito.when(transferAndBalanceRepository.findByName(cadastroDTO.getName())).thenReturn(transferEntity);
        Mockito.when(transferAndBalanceRepository.findByIssuerAndNumber(requestDTO.getCheckingAccountTo().getIssuer(), requestDTO.getCheckingAccountFrom().getNumber())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                service.checkingAccountTransfer(requestDTO));
    }

    @Test
    void consultBalance() throws Exception {

        RequestBalanceConsultDTO requestDTO = RequestBalanceConsultDTO.builder()
                .number("0001")
                .issuer("1234")
                .build();

        Optional<TransferAndBalanceConsultEntity> transferEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .balance(200.00)
                .number("0001")
                .issuer("1234")
                .build());

        Mockito.when(transferAndBalanceRepository.findByIssuerAndNumber(requestDTO.getIssuer(), requestDTO.getNumber())).thenReturn(transferEntity);

        Assertions.assertDoesNotThrow(
                () -> service.consultBalance(requestDTO));
    }

    @Test
    void consultBalanceError() throws Exception {

        RequestBalanceConsultDTO requestDTO = RequestBalanceConsultDTO.builder()
                .number("0001")
                .issuer("1234")
                .build();

        Mockito.when(transferAndBalanceRepository.findByIssuerAndNumber(requestDTO.getIssuer(), requestDTO.getNumber())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () ->
                service.consultBalance(requestDTO));
    }
}
