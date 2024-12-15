package com.mjw.mjwservice.validation.model;

import lombok.Builder;

@Builder
public record ErrorResponse(String message,
                            String detailedMessage,
                            ValidationResponse validationResponse) {

}
