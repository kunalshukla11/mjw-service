package com.mjw.mjwservice.validation.validator.user;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.UserLogin;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.model.group.UserRegister;
import com.mjw.mjwservice.validation.validator.RuleValidator;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import static com.mjw.mjwservice.validation.validator.RuleValidator.Type.SCHEMA;

@Component
public class UserInfoSchemaValidator implements RuleValidator<UserInfo> {

    private Validator validator;
    BiFunction<UserInfo, ValidationMode, List<Violation>> validateSchema = (userInfo, validationMode) -> {
        final Class<?> validationGroup = switch (validationMode) {
            case REGISTER_USER -> UserRegister.class;
            case LOGIN_USER -> UserLogin.class;
        };
        return validator.validate(userInfo, validationGroup)
                .stream()
                .map(v -> Violation.builder()
                        .field(v.getPropertyPath().toString())
                        .message(v.getMessage())
                        .build())
                .toList();
    };

    public UserInfoSchemaValidator(final Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<Violation> validate(final Validatable userInfo, final ValidationMode validationMode,
                                    final ValidationContext<? extends Validatable> context) {
        return validateSchema.apply((UserInfo) userInfo, validationMode);
    }

    @Override
    public Set<ValidationMode> supports() {
        return Set.of(ValidationMode.REGISTER_USER);
    }

    @Override
    public Type getType() {
        return SCHEMA;
    }

}
