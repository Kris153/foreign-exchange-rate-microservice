package com.example.fxservice.service;

import java.util.Optional;

public interface ConversionService {
    Optional<Double> getExchangeRate(String sourceCurrency, String targetCurrency);
}
