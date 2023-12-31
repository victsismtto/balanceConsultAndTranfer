package com.challange.api.TranferAndBalanceConsult.repository;

import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import com.challange.api.TranferAndBalanceConsult.model.entity.CheckingAccountTranferEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends MongoRepository<CheckingAccountTranferEntity, String> {
    CheckingAccountTranferEntity findByName(String name);
}
