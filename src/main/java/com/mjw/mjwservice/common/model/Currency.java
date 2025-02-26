package com.mjw.mjwservice.common.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Currency {
    INR("INR", "Indian Rupee"),
    USD("USD", "US Dollar"),
    EUR("EUR", "Euro"),
    GBP("GBP", "British Pound"),
    AUD("AUD", "Australian Dollar"),
    CAD("CAD", "Canadian Dollar"),
    CHF("CHF", "Swiss Franc"),
    CNY("CNY", "Chinese Yuan"),
    JPY("JPY", "Japanese Yen"),
    KRW("KRW", "South Korean Won"),
    MXN("MXN", "Mexican Peso"),
    RUB("RUB", "Russian Ruble");

    private final String code;
    private final String description;
}
