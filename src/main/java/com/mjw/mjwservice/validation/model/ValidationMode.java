package com.mjw.mjwservice.validation.model;

import com.mjw.mjwservice.user.model.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationMode {
    REGISTER_USER(UserInfo.class, true),
    LOGIN_USER(UserInfo.class, false);
    private final Class<?> modelClass;
    private final boolean defaultMode;
}


