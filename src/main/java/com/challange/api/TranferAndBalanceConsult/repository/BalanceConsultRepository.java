package com.challange.api.TranferAndBalanceConsult.repository;

import com.challange.api.TranferAndBalanceConsult.model.entity.BalanceConsultEntity;
import com.challange.api.TranferAndBalanceConsult.model.entity.CheckingAccountTranferEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceConsultRepository extends MongoRepository<BalanceConsultEntity, String> {

    Optional<BalanceConsultEntity> findByIssuerAndNumber(String issuer, String number);
}
