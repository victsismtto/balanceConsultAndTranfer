package com.challange.api.tranferAndBalanceConsult.mapper;

import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountTransferDTO;
import com.challange.api.tranferAndBalanceConsult.model.entity.TransferAndBalanceConsultEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {

    @InjectMocks
    private AccountMapper mapper;

    @Test
    void toBalanceConsultResponse() {
        TransferAndBalanceConsultEntity balanceConsultEntity = TransferAndBalanceConsultEntity.builder()
                .balance(200.00)
                .issuer("0001")
                .number("1234")
                .build();
        Assertions.assertDoesNotThrow(
                () -> mapper.toBalanceConsultResponse(balanceConsultEntity));
    }

    @Test
    void toCheckingAccountTransferResponse() {
        Assertions.assertDoesNotThrow(
                () -> mapper.toCheckingAccountTransferResponse());
    }

    @Test
    void totoBacenEntity() {

        CheckingAccountTo checkingAccountTo = CheckingAccountTo.builder()
            .issuer("0001")
            .number("1234")
            .build();

        CheckingAccountFrom checkingAccountFrom = CheckingAccountFrom.builder()
            .issuer("0001")
            .number("1234")
            .build();

        RequestCheckingAccountTransferDTO requestDTO = RequestCheckingAccountTransferDTO.builder()
            .checkingAccountFrom(checkingAccountFrom)
            .checkingAccountTo(checkingAccountTo)
            .transferAmount(200.00)
            .build();

        Assertions.assertDoesNotThrow(
                () -> mapper.toBacenEntity(requestDTO));
    }
}
