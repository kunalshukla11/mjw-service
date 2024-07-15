package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;

import java.util.Set;

public interface ValidationService<T> {

    Set<Violation> validate(Validatable t, ValidationMode validationMode);

    default Class<?> supports() {
        return null;
    }


}
