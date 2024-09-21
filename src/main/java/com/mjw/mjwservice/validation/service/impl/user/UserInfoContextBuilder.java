package com.mjw.mjwservice.validation.service.impl.user;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.model.context.UserInfoValidationContext;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.service.ValidationContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoContextBuilder implements ValidationContextBuilder {

    private final UserRepository userRepository;


    @Override
    public ValidationContext<? extends Validatable> build(final Validatable validatable,
                                                          final DefaultValidationContext defaultValidationContext,
                                                          final ValidationMode validationMode) {
        final UserInfo userInfo = (UserInfo) validatable;
        final UserInfoDatabaseImpl userInfoDatabase = userRepository.findByEmail(userInfo.email())
                .orElseThrow();

        return UserInfoValidationContext.builder().userInfoDatabase(userInfoDatabase).build();
    }

    @Override
    public Class<?> supports() {
        return UserInfo.class;
    }

}