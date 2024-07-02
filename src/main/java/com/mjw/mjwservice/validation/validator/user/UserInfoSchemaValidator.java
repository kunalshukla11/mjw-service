package com.mjw.mjwservice.validation.validator.user;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.validator.Validator;

import java.util.List;

public class UserInfoSchemaValidator implements Validator<UserInfo> {
    @Override
    public List<Violation> validate(final UserInfo userInfo, final ValidationMode validationMode) {
        return null;
    }

    @Override
    public List<ValidationMode> supports() {
        return null;
    }
}
