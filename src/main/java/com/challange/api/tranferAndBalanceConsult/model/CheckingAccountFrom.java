package com.challange.api.tranferAndBalanceConsult.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckingAccountFrom {

    @NotNull
    @Size(min = 1, max = 4)
    private String issuer;
    @NotNull
    @Size(min = 1, max = 20)
    private String number;
}
