package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.validator.RuleValidator;

import java.util.List;

public interface RuleValidatorProvider<T extends Validatable> {

    List<RuleValidator<? extends Validatable>> getValidators(T t, ValidationMode validationMode);

    Class<? extends Validatable> getSupportsType();

}
