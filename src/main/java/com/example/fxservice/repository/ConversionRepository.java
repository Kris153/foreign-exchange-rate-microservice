package com.example.fxservice.repository;

import com.example.fxservice.model.entities.ConversionEntity;
import com.example.fxservice.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionRepository extends JpaRepository<ConversionEntity, Integer> {
    List<ConversionEntity> findByUser(UserEntity user);
    List<ConversionEntity> findByTransactionIdentifierAndUser(String transactionIdentifier, UserEntity user);
    List<ConversionEntity> findBySourceCurrencyAndUser(String sourceCurrency, UserEntity user);
    List<ConversionEntity> findByTargetCurrencyAndUser(String targetCurrency, UserEntity user);
}
