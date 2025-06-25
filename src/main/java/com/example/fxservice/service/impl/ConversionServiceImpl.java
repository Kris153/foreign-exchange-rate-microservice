package com.example.fxservice.service.impl;

import com.example.fxservice.config.CurrencyApiConfig;
import com.example.fxservice.model.dtos.ConversionHistoryDTO;
import com.example.fxservice.model.dtos.CurrencyConversionDTO;
import com.example.fxservice.model.dtos.CurrencyLayerResponseDTO;
import com.example.fxservice.model.dtos.CurrencyListResponseDTO;
import com.example.fxservice.model.entities.ConversionEntity;
import com.example.fxservice.repository.ConversionRepository;
import com.example.fxservice.service.ConversionService;
import com.example.fxservice.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Service
public class ConversionServiceImpl implements ConversionService {
    private final RestClient restClient;
    private final String apiKey;
    private final ConversionRepository conversionRepository;
    private final UserService userService;

    public ConversionServiceImpl(RestClient currencyLayerRestClient, CurrencyApiConfig config, ConversionRepository conversionRepository, UserService userService) {
        this.restClient = currencyLayerRestClient;
        this.apiKey = config.getKey();
        this.conversionRepository = conversionRepository;
        this.userService = userService;
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

    @Override
    public ConversionHistoryDTO saveConversion(CurrencyConversionDTO currencyConversionDTO, Double exchangeRate) {
        ConversionEntity conversionToSave = new ConversionEntity();
        ConversionHistoryDTO conversionToReturn = new ConversionHistoryDTO();

        conversionToSave.setDateTime(Instant.now());
        conversionToSave.setSourceCurrency(currencyConversionDTO.getSourceCurrency().toUpperCase());
        conversionToSave.setTargetCurrency(currencyConversionDTO.getTargetCurrency().toUpperCase());
        conversionToSave.setQuantity(currencyConversionDTO.getQuantity());
        conversionToSave.setPricePerUnit(exchangeRate);
        conversionToSave.setTotalAmount(currencyConversionDTO.getQuantity() * exchangeRate);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSS"));
        String shortUuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        conversionToSave.setTransactionIdentifier("CONV-" + timestamp + "-" + shortUuid);
        conversionToSave.setUser(this.userService.getCurrentUser());
        this.conversionRepository.saveAndFlush(conversionToSave);

        conversionToReturn.setDateTime(conversionToSave.getDateTime());
        conversionToReturn.setSourceCurrency(conversionToSave.getSourceCurrency());
        conversionToReturn.setTargetCurrency(conversionToSave.getTargetCurrency());
        conversionToReturn.setQuantity(conversionToSave.getQuantity());
        conversionToReturn.setPricePerUnit(conversionToSave.getPricePerUnit());
        conversionToReturn.setTotalAmount(conversionToSave.getTotalAmount());
        conversionToReturn.setTransactionIdentifier(conversionToSave.getTransactionIdentifier());
        return conversionToReturn;
    }

    @CacheEvict("currencies")
    public void clearCurrencyCache() {}

}
