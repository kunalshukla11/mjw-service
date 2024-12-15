package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationResponse;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;

public interface ValidationService<K extends Validatable> {

    ValidationResponse validate(K k, DefaultValidationContext defaultValidationContext);

    Class<? extends Validatable> supportsType();


}
