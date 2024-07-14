package com.mjw.mjwservice.validation.model;

import lombok.Builder;

@Builder
public record Violation(String field,
                        String message) {

}
