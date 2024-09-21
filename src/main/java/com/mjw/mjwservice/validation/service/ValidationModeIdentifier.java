package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;

public interface ValidationModeIdentifier<T extends Validatable> {

    ValidationMode identify(Validatable t);

    Class<? extends Validatable> supportsType();

}
