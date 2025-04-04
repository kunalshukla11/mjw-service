package com.mjw.mjwservice.validation.service.impl;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.service.ValidationContextBuilder;
import org.springframework.stereotype.Component;

@Component
public class DefaultContextBuilder implements ValidationContextBuilder<Validatable> {

    @Override

    public ValidationContext build(final Validatable validatable,
                                                final DefaultValidationContext defaultValidationContext,
                                                final ValidationMode validationMode) {
        return  defaultValidationContext.withPayload(validatable);
    }

    @Override
    public Class<? extends Validatable> supportsType() {
        return Validatable.class;
    }

}
