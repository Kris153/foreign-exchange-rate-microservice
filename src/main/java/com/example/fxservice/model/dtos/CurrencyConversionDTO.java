package com.example.fxservice.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CurrencyConversionDTO {
    @NotBlank
    private String sourceCurrency;

    @NotBlank
    private String targetCurrency;

    @NotBlank
    @Positive
    private Double quantity;

    public CurrencyConversionDTO() {
    }

    public CurrencyConversionDTO(String sourceCurrency, String targetCurrency, Double quantity) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.quantity = quantity;
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
}
