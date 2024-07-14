package com.mjw.mjwservice.validation.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record ErrorResponse(String message,
                            Set<Violation> violations) {

}
