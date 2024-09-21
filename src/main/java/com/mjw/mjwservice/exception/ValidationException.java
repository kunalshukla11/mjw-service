package com.mjw.mjwservice.exception;

import com.mjw.mjwservice.validation.model.ValidationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {


    private ValidationResponse validationResponse;

    public ValidationException(final String message, final ValidationResponse validationResponse) {
        super(message);
        this.validationResponse = validationResponse;
    }

    public ValidationException(final String message) {
        super(message);
    }

}
