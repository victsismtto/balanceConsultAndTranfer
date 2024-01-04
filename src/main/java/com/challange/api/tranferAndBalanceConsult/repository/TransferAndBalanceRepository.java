package com.challange.api.tranferAndBalanceConsult.repository;

import com.challange.api.tranferAndBalanceConsult.model.entity.AccountsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferAndBalanceRepository extends MongoRepository<AccountsEntity, String> {
    Optional<AccountsEntity> findByName(String name);
    Optional<AccountsEntity> findByIssuerAndNumber(String issuer, String number);
}
