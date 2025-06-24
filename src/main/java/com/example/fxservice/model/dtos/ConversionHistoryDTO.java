package com.example.fxservice.model.dtos;

import java.time.LocalDateTime;

public class ConversionHistoryDTO {
    private Integer transactionIdentifier;
    private LocalDateTime dateTime;
    private String sourceCurrency;
    private String targetCurrency;
    private Double quantity;
    private Double pricePerUnit;
    private Double totalAmount;
}
