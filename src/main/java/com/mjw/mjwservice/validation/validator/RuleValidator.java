package com.mjw.mjwservice.validation.validator;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.ValidationContext;

import java.util.List;
import java.util.Set;

public interface RuleValidator<T> {

    List<Violation> validate(T t, ValidationMode validationMode,
                             ValidationContext context);

    Set<ValidationMode> supports();

    Class<? extends Validatable> validatingClass();

    default Type type() {
        return Type.BUSINESS;
    }

    enum Type {
        SCHEMA,
        BUSINESS
    }

}
