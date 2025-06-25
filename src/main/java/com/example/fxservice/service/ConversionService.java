package com.example.fxservice.service;

import com.example.fxservice.model.dtos.ConversionHistoryDTO;
import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
import com.example.fxservice.model.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ConversionService {
    CurrencyLayerResponseDTO getExchangeRate(String sourceCurrency, String targetCurrency);
    Set<String> getSupportedCurrencies();
    ConversionHistoryDTO saveConversion(CurrencyConversionDTO currencyConversionDTO, Double exchangeRate);
    List<ConversionHistoryDTO> getAllConversionsForUser();
    List<ConversionHistoryDTO> searchForConversions(String searchWord);
}
