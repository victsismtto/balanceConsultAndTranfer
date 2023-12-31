package com.challange.api.TranferAndBalanceConsult.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "BalanceConsult")
public class BalanceConsultEntity {

    @Id private String id;
    private String issuer;
    private String number;
    private String amount;

}
