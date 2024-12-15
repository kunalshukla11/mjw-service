package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.exception.ValidationException;
import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.ValidationResponse;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.service.impl.DefaultContextBuilder;
import com.mjw.mjwservice.validation.service.impl.DefaultRuleExecutor;
import com.mjw.mjwservice.validation.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mjw.mjwservice.validation.validator.RuleValidator.Type.BUSINESS;
import static com.mjw.mjwservice.validation.validator.RuleValidator.Type.SCHEMA;

@Component
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
@Log4j2
public class ValidationOrchestrator {

    private final List<ValidationContextBuilder<? extends Validatable>> contextBuilders;
    private final DefaultContextBuilder defaultContextBuilder;
    private final List<ValidationModeIdentifier<? extends Validatable>> validationModeIdentifiers;
    private final List<ValidationService<? extends Validatable>> validationServices;
    private final DefaultRuleExecutor defaultRuleExecutor;


    Object orchestrate(final Validatable payload,
                       final Class<? extends Validatable> validatingClass,
                       final DefaultValidationContext defaultValidationContext) {

        return Optional.ofNullable(orchestrateWithValidationResponse(payload, validatingClass,
                        defaultValidationContext))
                .map(ValidationResponse::fieldViolations)
                .filter(CollectionUtils::isNotEmpty)
                .map(violationList -> ValidationResponse.builder().fieldViolations(violationList).build())
                .map(validationResponse -> {
                    throw new ValidationException("Violations found during validations", validationResponse);
                })
                .orElse(payload);

    }

    public ValidationResponse orchestrateWithValidationResponse(
            final Validatable payload,
            final Class<? extends Validatable> validatingClass,
            final DefaultValidationContext defaultValidationContext) {
        final ValidationService<Validatable> validationService =
                (ValidationService<Validatable>) getValidationService(validatingClass);

        final ValidationResponse validationResponse = Optional.ofNullable(validationService)
                .map(service -> service.validate(payload, defaultValidationContext))
                .orElseGet(() -> executeValidations(payload, validatingClass, defaultValidationContext));

        if (CollectionUtils.isNotEmpty(validationResponse.fieldViolations())) {
            log.error("Validation Error: {}", validationResponse.toString());
        }

        return validationResponse;
    }

    public ValidationResponse executeValidations(final Validatable payload,
                                                 final Class<? extends Validatable> validatingClass,
                                                 final DefaultValidationContext defaultValidationContext) {
        final ValidationMode validationMode = getValidationMode(payload, validatingClass);

        final ValidationContextBuilder<Validatable> validationContextBuilder =
                (ValidationContextBuilder<Validatable>) getContextBuilder(validatingClass);

        final List<Violation> violations = Optional.of(
                        defaultRuleExecutor.execute(payload, validationMode, null, SCHEMA, validatingClass))
                .filter(CollectionUtils::isNotEmpty)
                .orElseGet(() -> {
                    final ValidationContext validationContext = validationContextBuilder.build(payload,
                            defaultValidationContext, validationMode);
                    return defaultRuleExecutor.execute(payload, validationMode, validationContext, BUSINESS,
                            validatingClass);
                });

        return ValidatorUtil.mapToValidationResponse(violations);
    }

    private ValidationContextBuilder<? extends Validatable> getContextBuilder(
            final Class<? extends Validatable> validatingClass) {
        return contextBuilders.stream()
                .filter(contextBuilder -> contextBuilder.supportsType().equals(validatingClass))
                .findFirst()
                .orElse(defaultContextBuilder);
    }

    private ValidationMode getValidationMode(final Validatable payload,
                                             final Class<? extends Validatable> validatingClass) {
        return validationModeIdentifiers.stream()
                .filter(identifier -> identifier.supportsType().equals(validatingClass))
                .findFirst()
                .map(vmi -> {
                    final ValidationModeIdentifier<Validatable> validationModeIdentifier =
                            (ValidationModeIdentifier<Validatable>) vmi;
                    return validationModeIdentifier.identify(payload);
                })
                .orElseGet(() -> getDefaultValidationMode(validatingClass));
    }

    private ValidationMode getDefaultValidationMode(final Class<? extends Validatable> validatingClass) {
        return Arrays.stream(ValidationMode.values())
                .filter(validationMode -> validationMode.getValidatingClass().equals(validatingClass))
                .filter(ValidationMode::getIsDefault)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No default validation can be derived for class: " + validatingClass.getName()));
    }


    private ValidationService<? extends Validatable> getValidationService(
            final Class<? extends Validatable> validatingClass) {
        return validationServices.stream()
                .filter(service -> service.supportsType().equals(validatingClass))
                .findFirst()
                .orElse(null);
    }

}
