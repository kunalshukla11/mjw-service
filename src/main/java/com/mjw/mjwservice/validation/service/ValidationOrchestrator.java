package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.exception.ValidationException;
import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ErrorResponse;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.service.impl.DefaultValidationModeIdentifier;
import com.mjw.mjwservice.validation.service.impl.DefaultValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidationOrchestrator {

    private final List<ValidationService<? extends Validatable>> validationServices;
    private final DefaultValidationService<? extends Validatable> defaultValidationService;
    private final List<ValidationModeIdentifier<? extends Validatable>> validationModeIdentifiers;
    private final DefaultValidationModeIdentifier<? extends Validatable> defaultValidationModeIdentifier;



    public <T extends Validatable> void validate(final T validatable,
                                                 final ValidationMode validationMode) {
        final ValidationService<? extends Validatable> validationService = getValidationService(validatable);

        final Set<Violation> violations = validationService.validate(validatable, validationMode);
        if (!violations.isEmpty()) {
            throw new ValidationException("Validation failed", violations);
        }
    }


    public ErrorResponse validateWithResponse(final Validatable validatable) {
        return ErrorResponse.builder().build();
    }

    private ValidationService<? extends Validatable> getValidationService(final Validatable validatable) {
        return validationServices.stream()
                .filter(v -> validatable.getClass().equals(v.supports()))
                .findFirst()
                .orElse(defaultValidationService);
    }



    private ValidationModeIdentifier<? extends Validatable> getValidationModeIdentifier(final Validatable validatable) {
        return validationModeIdentifiers.stream()
                .filter(v -> validatable.getClass().equals(v.supports()))
                .findFirst()
                .orElse(defaultValidationModeIdentifier);
    }

}
