package com.challange.api.TranferAndBalanceConsult.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCheckingAccountTransferDTO {

    private String message;
    private String date;
}
