package com.mjw.mjwservice.validation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
public record Violation(@JsonIgnore String field,
                        String message,
                        ViolationType violationType) {

    @RequiredArgsConstructor
    @Getter
    public enum ViolationType {
        ERROR,
        WARNING,
        INFO
    }

}
