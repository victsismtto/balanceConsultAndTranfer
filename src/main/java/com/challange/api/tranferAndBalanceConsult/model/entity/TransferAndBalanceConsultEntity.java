package com.challange.api.tranferAndBalanceConsult.model.entity;

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
@Document(collection = "Accounts")
public class TransferAndBalanceConsultEntity {

    @Id
    private String _id;
    private String name;
    private Boolean isActive;
    private String issuer;
    private String number;
    private Double balance;
    private Double dailyLimitUsed;
    private String date;
}
