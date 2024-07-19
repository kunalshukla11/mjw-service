package com.mjw.mjwservice.validation.validator.user;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.UserInfoValidationContext;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.validator.RuleValidator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserExistsValidator implements RuleValidator<UserInfo> {

    @Override
    public List<Violation> validate(final Validatable t, final ValidationMode validationMode, final ValidationContext<?
            extends Validatable> context) {
        final UserInfoValidationContext userInfoValidationContext = (UserInfoValidationContext) context;
        return Optional.ofNullable(userInfoValidationContext.userInfoDatabase())
                .map(userInfoDatabase -> List.of(Violation.builder()
                        .field("email")
                        .message("user already exists")
                        .build()))
                .orElse(Collections.emptyList());
    }

    @Override
    public Set<ValidationMode> supports() {
        return Set.of(ValidationMode.REGISTER_USER);
    }

}
