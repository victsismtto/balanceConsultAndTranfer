package com.challange.api.TranferAndBalanceConsult.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBalanceConsultDTO {

    private String balance;
    private String date;

}
