package com.mjw.mjwservice.validation.validator;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;

import java.util.List;
import java.util.Set;

public interface RuleValidator<T> {

    List<Violation> validate(Validatable t, ValidationMode validationMode);

    Set<ValidationMode> supports();

    default Type getType() {
        return Type.BUSINESS;
    }

    enum Type {
        SCHEMA,
        BUSINESS
    }

}
