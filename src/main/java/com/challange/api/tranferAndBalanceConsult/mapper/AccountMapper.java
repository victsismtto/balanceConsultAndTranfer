package com.challange.api.tranferAndBalanceConsult.mapper;

import com.challange.api.tranferAndBalanceConsult.model.dto.RequestCheckingAccountDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.tranferAndBalanceConsult.model.dto.ResponseCheckingAccountDTO;
import com.challange.api.tranferAndBalanceConsult.model.entity.BacenTransferEntity;
import com.challange.api.tranferAndBalanceConsult.model.entity.AccountsEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class AccountMapper {
    public ResponseBalanceConsultDTO toBalanceConsultResponse(AccountsEntity entity) {
        return ResponseBalanceConsultDTO.builder()
                .balance(entity.getBalance().toString())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss'Z'")))
                .build();
    }

    public ResponseCheckingAccountDTO toCheckingAccountTransferResponse() {
        return ResponseCheckingAccountDTO.builder()
                .message("The transaction was made with success!")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss'Z'")))
                .build();
    }

    public BacenTransferEntity toBacenEntity(RequestCheckingAccountDTO requestDTO) {
        return BacenTransferEntity.builder()
                .accountTo(requestDTO.getCheckingAccountTo())
                .accountFrom(requestDTO.getCheckingAccountFrom())
                .transactionAmount(requestDTO.getTransferAmount())
                .completed(false)
                .build();
    }
}
