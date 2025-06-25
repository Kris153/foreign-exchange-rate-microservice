package com.example.fxservice.model.dtos;

import java.time.Instant;

public class ConversionHistoryDTO {
    private String transactionIdentifier;
    private Instant dateTime;
    private String sourceCurrency;
    private String targetCurrency;
    private Double quantity;
    private Double pricePerUnit;
    private Double totalAmount;

    public ConversionHistoryDTO() {
    }

    public ConversionHistoryDTO(String transactionIdentifier, Instant dateTime, String sourceCurrency, String targetCurrency, Double quantity, Double pricePerUnit, Double totalAmount) {
        this.transactionIdentifier = transactionIdentifier;
        this.dateTime = dateTime;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount = totalAmount;
    }

    public String getTransactionIdentifier() {
        return this.transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
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
}