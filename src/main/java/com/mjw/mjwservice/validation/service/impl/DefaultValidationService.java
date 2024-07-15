package com.mjw.mjwservice.validation.service.impl;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.service.ValidationContextBuilder;
import com.mjw.mjwservice.validation.service.ValidationService;
import com.mjw.mjwservice.validation.validator.RuleValidator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mjw.mjwservice.validation.validator.RuleValidator.Type.BUSINESS;
import static com.mjw.mjwservice.validation.validator.RuleValidator.Type.SCHEMA;

@Service
@RequiredArgsConstructor
public class DefaultValidationService<T extends Validatable> implements ValidationService<T> {

    private final List<RuleValidator<? extends Validatable>> ruleValidators;
    private final List<ValidationContextBuilder> validationContextBuilders;
    private final DefaultContextBuilder defaultContextBuilder;


    @Override
    public Set<Violation> validate(final Validatable validatable, final ValidationMode validationMode) {


        final Set<RuleValidator<? extends Validatable>> applicableValidators = ruleValidators.stream()
                .filter(v -> v.supports().contains(validationMode))
                .collect(Collectors.toSet());

        final ValidationContextBuilder validationContextBuilder = getValidationContextBuilder(validatable);

        return Optional.of(validate(validatable, validationMode, applicableValidators, SCHEMA, null))
                .filter(CollectionUtils::isNotEmpty)
                .orElseGet(() -> {
                    final ValidationContext<? extends Validatable> context =
                            validationContextBuilder.build(validatable, null,
                            validationMode);
                    return validate(validatable, validationMode, applicableValidators, BUSINESS, context);
                });
    }

    private Set<Violation> validate(final Validatable validatable, final ValidationMode validationMode,
                                    final Set<RuleValidator<? extends Validatable>> applicableValidators,
                                    final RuleValidator.Type type,
                                    final ValidationContext<? extends Validatable> context) {

        return applicableValidators.stream()
                .filter(v -> v.getType().equals(type))
                .map(v -> v.validate(validatable, validationMode, context))
                .flatMap(List::stream)
                .collect(Collectors.toSet());

    }

    private ValidationContextBuilder getValidationContextBuilder(final Validatable validatable) {
        return validationContextBuilders.stream()
                .filter(v -> validatable.getClass().equals(v.supports()))
                .findFirst()
                .orElse(defaultContextBuilder);
    }

}
