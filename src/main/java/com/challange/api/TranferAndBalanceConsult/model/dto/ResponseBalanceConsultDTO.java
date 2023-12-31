package com.challange.api.TranferAndBalanceConsult.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBalanceConsultDTO {

    private String amount;
    private String data;

}
