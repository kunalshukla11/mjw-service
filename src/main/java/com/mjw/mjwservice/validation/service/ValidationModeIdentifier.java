package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;

public interface ValidationModeIdentifier<T> {

    ValidationMode identify(Validatable t);

    default Class<?> supports() {
        return null;
    }

}
