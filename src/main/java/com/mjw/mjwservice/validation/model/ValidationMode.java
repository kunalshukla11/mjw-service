package com.mjw.mjwservice.validation.model;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.model.Validatable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationMode {
    REGISTER_USER(UserInfo.class, true),
    LOGIN_USER(UserInfo.class);
    private final Class<? extends Validatable> validatingClass;
    private final Boolean isDefault;

    ValidationMode(final Class<? extends Validatable> validatableClass) {
        this.validatingClass = validatableClass;
        this.isDefault = Boolean.FALSE;

    }
}


