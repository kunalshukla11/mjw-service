package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericValidationService<T> {

    private final List<Validator<T>> validators;

    public void validate(final T t, final ValidationMode mode) {
        validators.stream()
                .filter(v -> v.supports().contains(mode))
                .filter(v -> v.supports().contains(mode))
                .forEach(v -> v.validate(t, mode));
    }

}
