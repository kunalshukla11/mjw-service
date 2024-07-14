package com.mjw.mjwservice.exception;

import com.mjw.mjwservice.validation.model.Violation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends RuntimeException {

    private Set<Violation> violations;

    public ValidationException(final String message, final Set<Violation> violations) {
        super(message);
        this.violations = violations;
    }

    public ValidationException(final String message) {
        super(message);
    }

}
