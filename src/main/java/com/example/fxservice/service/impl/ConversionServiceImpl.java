package com.example.fxservice.service.impl;

import com.example.fxservice.config.CurrencyApiConfig;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
import com.example.fxservice.service.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class ConversionServiceImpl implements ConversionService {
    private final RestClient restClient;
    private final String apiKey;

    public ConversionServiceImpl(RestClient currencyLayerRestClient, CurrencyApiConfig config) {
        this.restClient = currencyLayerRestClient;
        this.apiKey = config.getKey();
    }

    @Override
    public Optional<Double> getExchangeRate(String sourceCurrency, String targetCurrency) {
        CurrencyLayerResponseDTO response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/live")
                        .queryParam("access_key", apiKey)
                        .queryParam("currencies", targetCurrency)
                        .queryParam("source", sourceCurrency)
                        .queryParam("format", 1)
                        .build()
                )
                .retrieve()
                .body(CurrencyLayerResponseDTO.class);

        if (response == null || !response.isSuccess()) {
            return Optional.empty();
        }

        String rateKey = sourceCurrency.toUpperCase() + targetCurrency.toUpperCase();

        if (response.getQuotes() == null || !response.getQuotes().containsKey(rateKey)) {
            return Optional.empty();
        }

        return Optional.of(response.getQuotes().get(rateKey));
    }
}
