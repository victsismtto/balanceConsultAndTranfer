package com.challange.api.tranferAndBalanceConsult.mapper;

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
}
