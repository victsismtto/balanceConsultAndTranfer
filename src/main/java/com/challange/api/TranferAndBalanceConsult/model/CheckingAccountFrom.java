package com.challange.api.TranferAndBalanceConsult.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckingAccountFrom {
    private String issuer;
    private String number;
}