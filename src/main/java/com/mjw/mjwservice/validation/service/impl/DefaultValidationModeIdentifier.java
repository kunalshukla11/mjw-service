package com.mjw.mjwservice.validation.service.impl;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.service.ValidationModeIdentifier;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class DefaultValidationModeIdentifier<T extends Validatable> implements ValidationModeIdentifier<T> {

    @Override
    public ValidationMode identify(final Validatable validatable) {
        return Arrays.stream(ValidationMode.values())
                .filter(validationMode -> validationMode.getValidatingClass().equals(validatable.getClass()))
                .filter(ValidationMode::getIsDefault)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No default mode found for: " + validatable.getClass()
                        .getName()));
    }

    @Override
    public Class<? extends Validatable> supportsType() {
        return null;
    }

    @PostConstruct
    void init() {
        final Map<Class<?>, ValidationMode> defaultModeMap = new java.util.HashMap<>();
        for (ValidationMode mode : ValidationMode.values()) {
            if (mode.getIsDefault()) {
                if (defaultModeMap.containsKey(mode.getValidatingClass())) {
                    throw new IllegalStateException(
                            "Multiple enum constants with defaultMode=true for model class: "
                                    + mode.getValidatingClass().getName()
                    );
                }
                defaultModeMap.put(mode.getValidatingClass(), mode);
            }
        }
    }

}
