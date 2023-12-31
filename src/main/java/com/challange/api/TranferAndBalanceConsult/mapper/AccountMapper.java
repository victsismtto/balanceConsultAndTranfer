package com.challange.api.TranferAndBalanceConsult.mapper;

import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseCheckingAccountTransferDTO;
import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class AccountMapper {
    public ResponseBalanceConsultDTO toBalanceConsultResponse(BalanceConsultEntity entity) {
        return ResponseBalanceConsultDTO.builder()
                .balance(entity.getBalance().toString())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss'Z'")))
                .build();
    }

    public ResponseCheckingAccountTransferDTO toCheckingAccountTransferResponse() {
        return ResponseCheckingAccountTransferDTO.builder()
                .message("The transaction was made with success!")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss'Z'")))
                .build();
    }
}
