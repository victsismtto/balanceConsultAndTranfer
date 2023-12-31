package com.challange.api.TranferAndBalanceConsult.model.dto;

import com.challange.api.TranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.TranferAndBalanceConsult.model.CheckingAccountTo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCheckingAccountTransferDTO {

    private String id;
    private Double transferAmount;
    private CheckingAccountFrom checkingAccountFrom;
    private CheckingAccountTo checkingAccountTo;
}
