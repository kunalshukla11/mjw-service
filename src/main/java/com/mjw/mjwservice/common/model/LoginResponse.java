package com.mjw.mjwservice.common.model;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long userId,
        String message,
        SuccessFailure successFailure
) {
    public enum SuccessFailure {
        SUCCESS, FAILURE
    }
}
