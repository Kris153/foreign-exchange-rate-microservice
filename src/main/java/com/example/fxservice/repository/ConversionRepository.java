package com.example.fxservice.repository;

import com.example.fxservice.model.entities.ConversionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<ConversionEntity, Integer> {

}
