package com.mjw.mjwservice.validation.model;

import lombok.Builder;

@Builder
public record ErrorResponse(String message,
                            String detailedMessage,
                            boolean isHandled,
                            ValidationResponse validationResponse) {

}
