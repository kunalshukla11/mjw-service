package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.model.context.ValidationContext;


public interface ValidationContextBuilder {

    ValidationContext<? extends Validatable> build(Validatable validatable,
                                                   DefaultValidationContext defaultValidationContext,
                                                   ValidationMode validationMode);

    Class<?> supports();

}
