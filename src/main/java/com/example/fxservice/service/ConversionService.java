package com.example.fxservice.service;

import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;

import java.util.Optional;
import java.util.Set;

public interface ConversionService {
    CurrencyLayerResponseDTO getExchangeRate(String sourceCurrency, String targetCurrency);
    Set<String> getSupportedCurrencies();
}
