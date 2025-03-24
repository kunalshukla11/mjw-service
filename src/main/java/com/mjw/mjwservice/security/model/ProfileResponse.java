package com.mjw.mjwservice.security.model;

import lombok.Builder;
import lombok.With;

@Builder
public record ProfileResponse(
        Long id,
        @With
        SuccessFailure successFailure,
        String email,
        String firstName,
        String initial,
        @With
        String message
) {

    public enum SuccessFailure {
        SUCCESS,
        FAILURE
    }

}

