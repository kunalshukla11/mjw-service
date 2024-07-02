package com.mjw.mjwservice.validation.validator;

import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;

import java.util.List;

public interface Validator<T> {
    List<Violation> validate(T t, ValidationMode validationMode);

    List<ValidationMode> supports();
}
