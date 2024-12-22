package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.model.context.ValidationContext;


public interface ValidationContextBuilder<T extends Validatable> {

    ValidationContext build(T validatable,
                            DefaultValidationContext defaultValidationContext,
                            ValidationMode validationMode);

    Class<? extends Validatable> supportsType();

}
