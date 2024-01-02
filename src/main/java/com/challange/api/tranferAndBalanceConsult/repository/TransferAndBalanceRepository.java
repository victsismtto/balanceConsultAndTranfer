package com.challange.api.tranferAndBalanceConsult.repository;

import com.challange.api.tranferAndBalanceConsult.model.entity.TransferAndBalanceConsultEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferAndBalanceRepository extends MongoRepository<TransferAndBalanceConsultEntity, String> {
    Optional<TransferAndBalanceConsultEntity> findByName(String name);
    Optional<TransferAndBalanceConsultEntity> findByIssuerAndNumber(String issuer, String number);
}
