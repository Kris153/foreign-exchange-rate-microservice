package com.example.fxservice.service.impl;

import com.example.fxservice.config.CurrencyApiConfig;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
import com.example.fxservice.model.dtos.CurrencyListResponseDTO;
import com.example.fxservice.service.ConversionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;
import java.util.Set;

@Service
public class ConversionServiceImpl implements ConversionService {
    private final RestClient restClient;
    private final String apiKey;

    public ConversionServiceImpl(RestClient currencyLayerRestClient, CurrencyApiConfig config) {
        this.restClient = currencyLayerRestClient;
        this.apiKey = config.getKey();
    }
    @Cacheable(cacheNames = "rates", key = "#sourceCurrency + '_' + #targetCurrency")
    @Override
    public CurrencyLayerResponseDTO getExchangeRate(String sourceCurrency, String targetCurrency) {
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
            String error = "Failed to get exchange rate: " + (response != null ? response.getError() : "No response");
            if(response == null){
                response = new CurrencyLayerResponseDTO();
            }
            if (response.getError() == null){
                response.setError(new CurrencyLayerResponseDTO.ErrorResponse());
            }
            response.getError().setInfo(error);
            response.setSuccess(false);
            return response;
        }

        String rateKey = sourceCurrency.toUpperCase() + targetCurrency.toUpperCase();

        if (response.getQuotes() == null || !response.getQuotes().containsKey(rateKey)) {
            if (response.getError() == null){
                response.setError(new CurrencyLayerResponseDTO.ErrorResponse());
            }
            response.getError().setInfo("Exchange rate for " + rateKey + " not found.");
            response.setSuccess(false);
            return response;
        }

        return response;
    }
    @CacheEvict(cacheNames = "rates", key = "#sourceCurrency + '_' + #targetCurrency")
    public void clearRateCache(String sourceCurrency, String targetCurrency) {}
    @Cacheable("currencies")
    public Set<String> getSupportedCurrencies() {
        CurrencyListResponseDTO response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/list")
                        .queryParam("access_key", apiKey)
                        .build())
                .retrieve()
                .body(CurrencyListResponseDTO.class);

        return response != null && response.isSuccess() ? response.getCurrencies().keySet() : Set.of();
    }
    @CacheEvict("currencies")
    public void clearCurrencyCache() {}

}
