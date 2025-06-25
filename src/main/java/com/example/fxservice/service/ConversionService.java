package com.example.fxservice.service;

import com.example.fxservice.model.dtos.ConversionHistoryDTO;
import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
import com.example.fxservice.model.entities.UserEntity;

import java.util.Optional;
import java.util.Set;

public interface ConversionService {
    CurrencyLayerResponseDTO getExchangeRate(String sourceCurrency, String targetCurrency);
    Set<String> getSupportedCurrencies();
    ConversionHistoryDTO saveConversion(CurrencyConversionDTO currencyConversionDTO, Double exchangeRate);
}
