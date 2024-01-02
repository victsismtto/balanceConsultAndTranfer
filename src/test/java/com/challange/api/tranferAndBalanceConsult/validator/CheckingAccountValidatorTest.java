package com.challange.api.tranferAndBalanceConsult.validator;

import com.challange.api.tranferAndBalanceConsult.exception.BusinessException;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.tranferAndBalanceConsult.model.entity.TransferAndBalanceConsultEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CheckingAccountValidatorTest {

    @InjectMocks
    private CheckingAccountValidator validator;

    @Test
    void testTransferAndReceiverAccountValidations() {
        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
            .issuer("0002")
            .number("0123")
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
            .issuer("0001")
            .number("1234")
            .dailyLimitUsed(0.00)
            .build());

        Optional<TransferAndBalanceConsultEntity> receiverEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
            .name("Client_2")
            .isActive(true)
            .date("01-01-2023")
            .issuer("0002")
            .number("0123")
            .balance(200.00)
            .dailyLimitUsed(0.00)
            .build());

        Assertions.assertDoesNotThrow(
            () -> validator.transferAndReceiverAccountValidations(transferEntity, receiverEntity, requestDTO));
    }

    @Test
    void testTransferAndReceiverAccountValidationsErrorSecondAccountIssuerNumber() {
        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0002")
                .number("0123")
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
                .issuer("0002")
                .number("0123")
                .dailyLimitUsed(0.00)
                .build());

        Optional<TransferAndBalanceConsultEntity> receiverEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_2")
                .isActive(true)
                .date("01-01-2023")
                .issuer("0002")
                .number("0123")
                .balance(200.00)
                .dailyLimitUsed(0.00)
                .build());

        Assertions.assertThrows(BusinessException.class,
                () -> validator.transferAndReceiverAccountValidations(transferEntity, receiverEntity, requestDTO));
    }

    @Test
    void testTransferAndReceiverAccountValidationsErrorNoActive() {
        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0002")
                .number("0123")
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
                .isActive(false)
                .date("01-01-2023")
                .balance(200.00)
                .issuer("0001")
                .number("1234")
                .dailyLimitUsed(0.00)
                .build());

        Optional<TransferAndBalanceConsultEntity> receiverEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_2")
                .isActive(false)
                .date("01-01-2023")
                .issuer("0002")
                .number("0123")
                .balance(200.00)
                .dailyLimitUsed(0.00)
                .build());

        Assertions.assertThrows(BusinessException.class,
                () -> validator.transferAndReceiverAccountValidations(transferEntity, receiverEntity, requestDTO));
    }

    @Test
    void testTransferAndReceiverAccountValidationsErrorTransferBiggerBalance() {
        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0002")
                .number("0123")
                .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
                .issuer("0001")
                .number("1234")
                .build();

        RequestCheckingAccountTransferDTO requestDTO = RequestCheckingAccountTransferDTO.builder()
                .checkingAccountTo(checkingAccountTo)
                .checkingAccountFrom(checkingAccountFrom)
                .transferAmount(500.00)
                .idBank("1")
                .build();

        Optional<TransferAndBalanceConsultEntity> transferEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_1")
                .isActive(true)
                .date("01-01-2023")
                .balance(200.00)
                .issuer("0001")
                .number("1234")
                .dailyLimitUsed(0.00)
                .build());

        Optional<TransferAndBalanceConsultEntity> receiverEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_2")
                .isActive(true)
                .date("01-01-2023")
                .issuer("0002")
                .number("0123")
                .balance(200.00)
                .dailyLimitUsed(0.00)
                .build());

        Assertions.assertThrows(BusinessException.class,
                () -> validator.transferAndReceiverAccountValidations(transferEntity, receiverEntity, requestDTO));
    }

    @Test
    void testTransferAndReceiverAccountValidationsErrorDailyAmount() {

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
                .issuer("0002")
                .number("0123")
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
                .date(today)
                .balance(200.00)
                .issuer("0001")
                .number("1234")
                .dailyLimitUsed(900.00)
                .build());

        Optional<TransferAndBalanceConsultEntity> receiverEntity = Optional.ofNullable(TransferAndBalanceConsultEntity.builder()
                .name("Client_2")
                .isActive(true)
                .date(today)
                .issuer("0002")
                .number("0123")
                .balance(200.00)
                .dailyLimitUsed(0.00)
                .build());

        Assertions.assertThrows(BusinessException.class,
                () -> validator.transferAndReceiverAccountValidations(transferEntity, receiverEntity, requestDTO));
    }
}
