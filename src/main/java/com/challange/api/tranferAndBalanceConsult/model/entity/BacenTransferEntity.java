package com.challange.api.tranferAndBalanceConsult.model.entity;

import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountFrom;
import com.challange.api.tranferAndBalanceConsult.model.CheckingAccountTo;
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
@Document(collection = "PersistenceAccounts")
public class BacenTransferEntity {


    @Id
    private String _id;
    private CheckingAccountTo accountTo;
    private CheckingAccountFrom accountFrom;
    private Double transactionAmount;
    private Boolean completed;
}
