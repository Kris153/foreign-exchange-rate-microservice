package com.example.fxservice.model.dtos;

import jakarta.validation.constraints.NotBlank;

public class ExchangeRateDTO {
    @NotBlank(message = "This field should not be empty")
    private String sourceCurrency;
    @NotBlank(message = "This field should not be empty")
    private String targetCurrency;

    private Double exchangeRate;

    public ExchangeRateDTO(String sourceCurrency, String targetCurrency, Double exchangeRate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRate = exchangeRate;
    }

    public ExchangeRateDTO() {
    }

    public Double getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
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

