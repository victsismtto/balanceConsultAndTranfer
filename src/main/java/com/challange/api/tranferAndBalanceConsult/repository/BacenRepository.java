package com.challange.api.tranferAndBalanceConsult.repository;

import com.challange.api.tranferAndBalanceConsult.model.entity.BacenTransferEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BacenRepository extends MongoRepository<BacenTransferEntity, String> {

    Optional<List<BacenTransferEntity>> findByCompletedIsFalse();
}
