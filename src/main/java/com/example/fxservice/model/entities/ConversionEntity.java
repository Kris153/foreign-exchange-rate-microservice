package com.example.fxservice.model.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversions")
public class ConversionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "date_time")
    private Instant dateTime;

    @Column(nullable = false, name = "source_currency")
    private String sourceCurrency;

    @Column(nullable = false, name = "target_currency")
    private String targetCurrency;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false, name = "price_per_unit")
    private Double pricePerUnit;

    @Column(nullable = false, name = "total_amount")
    private Double totalAmount;

    @Column(nullable = false, unique = true, name = "transaction_identifier")
    private String transactionIdentifier;

    @ManyToOne
    private UserEntity user;

    public ConversionEntity() {
    }

    public ConversionEntity(Integer id, Instant dateTime, String sourceCurrency, String targetCurrency, Double quantity, Double pricePerUnit, Double totalAmount, String transactionIdentifier, UserEntity user) {
        this.id = id;
        this.dateTime = dateTime;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount = totalAmount;
        this.transactionIdentifier = transactionIdentifier;
        this.user = user;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public String getSourceCurrency() {
        return this.sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return this.targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return this.pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTransactionIdentifier() {
        return this.transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    public UserEntity getUser() {
        return this.user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
