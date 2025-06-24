package com.example.fxservice.model.dtos;

import jakarta.validation.constraints.NotBlank;

public class ExchangeRateDTO {
    @NotBlank
    private String sourceCurrency;
    @NotBlank
    private String targetCurrency;

    public ExchangeRateDTO() {
    }

    public ExchangeRateDTO(String sourceCurrency, String targetCurrency) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
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
}

