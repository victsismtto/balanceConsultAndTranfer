package com.challange.api.tranferAndBalanceConsult.model.dto;

import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCheckingAccountTransferDTO {

    @NotNull
    private String idBank;
    @NotNull
    private Double transferAmount;
    @NotNull
    private CheckingAccountFrom checkingAccountFrom;
    @NotNull
    private CheckingAccountTo checkingAccountTo;
}
