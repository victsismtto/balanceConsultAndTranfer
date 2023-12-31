package com.challange.api.TranferAndBalanceConsult.repository;

import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceConsultRepository extends MongoRepository<BalanceConsultEntity, String> {

    BalanceConsultEntity findByIssuerAndNumber(String issuer, String number);
}
