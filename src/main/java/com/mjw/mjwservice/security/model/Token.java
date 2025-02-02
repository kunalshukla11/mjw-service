package com.mjw.mjwservice.security.model;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record Token(TokenType tokenType,
                    String tokenValue,
                    Long duration,
                    LocalDateTime expiryDate) {

    @RequiredArgsConstructor
    @Getter
    public enum TokenType {
        ACCESS("auth_token"),
        REFRESH("refresh_token");
        private final String description;
    }

}
