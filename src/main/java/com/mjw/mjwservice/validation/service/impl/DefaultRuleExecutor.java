package com.mjw.mjwservice.validation.service.impl;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.RuleIndex;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.service.RuleValidatorProvider;
import com.mjw.mjwservice.validation.service.RulesExecutor;
import com.mjw.mjwservice.validation.validator.RuleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Log4j2
@SuppressWarnings("unchecked")
public class DefaultRuleExecutor implements RulesExecutor<Validatable> {

    private final List<RuleValidator<? extends Validatable>> ruleValidators;
    private final List<RuleValidatorProvider<? extends Validatable>> ruleValidatorProviders;

    @Override
    public List<Violation> execute(final Validatable payload, final ValidationMode validationMode,
                                   final ValidationContext validationContext, final RuleValidator.Type type,
                                   final Class<?
            extends Validatable> validatingClass) {
        final List<RuleValidator<? extends Validatable>> applicableValidators = getApplicableValidators(payload,
                validatingClass, validationMode, type);

        log.info("Applying Validators for class: {} | Validation mode: {} | Type: {} | Validators: {}",
                validatingClass.getSimpleName(),
                validationMode,
                type,
                validators(applicableValidators));



        return applicableValidators.stream()
                .flatMap(validator -> {
                    final RuleValidator<Validatable> ruleValidator = (RuleValidator<Validatable>) validator;
                    return ruleValidator.validate(payload, validationMode, validationContext).stream();
                }).toList();
    }


    private List<RuleValidator<? extends Validatable>> getApplicableValidators(final Validatable payload, final Class<?
            extends Validatable> validatingClass, final ValidationMode validationMode, final RuleValidator.Type type) {
        final List<RuleValidator<? extends Validatable>> applicableValidators = ruleValidatorProviders.stream()
                .filter(ruleValidatorProvider -> ruleValidatorProvider.getSupportsType().equals(validatingClass))
                .findFirst()
                .map(validatorProvider -> {
                    final RuleValidatorProvider<Validatable> ruleValidatorProvider =
                            (RuleValidatorProvider<Validatable>) validatorProvider;
                    return ruleValidatorProvider.getValidators(payload, validationMode);
                })
                .orElse(ruleValidators);

        return applicableValidators.stream()
                .filter(validator -> validator.validatingClass().equals(validatingClass))
                .filter(validator -> validator.type().equals(type))
                .filter(validator -> validator.supports().contains(validationMode))
                .toList();

    }

    @Override
    public RuleIndex getRuleIndex(final Validatable payload, final Class<? extends Validatable> validatingClass,
                                  final ValidationMode validationMode) {

        final Set<String> validators = Optional.of(payload)
                .map(payl -> {
                    final List<RuleValidator<? extends Validatable>>  ruleValidatorList = new ArrayList<>();
                    ruleValidatorList.addAll(this.getApplicableValidators(payl, validatingClass, validationMode,
                            RuleValidator.Type.SCHEMA));
                    ruleValidatorList.addAll(this.getApplicableValidators(payl, validatingClass, validationMode,
                            RuleValidator.Type.BUSINESS));
                    return ruleValidatorList;
                })
                .filter(CollectionUtils::isNotEmpty)
                .map(validatorList -> validatorList.stream()
                        .map(validator -> validator.getClass().getSimpleName())
                        .collect(Collectors.toSet()))
                .orElseGet(Collections::emptySet);

        return RuleIndex.builder()
                .validationMode(validationMode)
                .validators(validators)
                .build();
    }

    private static String validators(final List<RuleValidator<? extends Validatable>> applicableValidators) {
        return applicableValidators.stream().map(validator -> Optional.ofNullable(validator.getClass())
                        .map(Class::getSimpleName)
                        .orElse("-"))
                .collect(Collectors.joining(", "));
    }

}
