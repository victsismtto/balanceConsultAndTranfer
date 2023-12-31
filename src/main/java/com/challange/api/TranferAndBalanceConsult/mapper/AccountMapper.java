package com.challange.api.TranferAndBalanceConsult.mapper;

import com.challange.api.TranferAndBalanceConsult.model.dto.ResponseBalanceConsultDTO;
import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
public class AccountMapper {
    public ResponseBalanceConsultDTO toBalanceConsultResponse(BalanceConsultEntity entity) {
        return ResponseBalanceConsultDTO.builder()
                .amount(entity.getAmount())
                .build();
    }
}
