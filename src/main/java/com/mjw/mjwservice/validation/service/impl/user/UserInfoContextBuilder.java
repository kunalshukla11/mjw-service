package com.mjw.mjwservice.validation.service.impl.user;

import com.mjw.mjwservice.user.entity.UserInfoDb;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.model.context.UserInfoValidationContext;
import com.mjw.mjwservice.validation.service.ValidationContextBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoContextBuilder implements ValidationContextBuilder<UserInfo> {

    private final UserRepository userRepository;


    @Override
    public UserInfoValidationContext build(final UserInfo userInfo,
                                                          final DefaultValidationContext defaultValidationContext,
                                                          final ValidationMode validationMode) {
        final UserInfoDb userInfoDatabase = userRepository.findByEmail(userInfo.email())
                .orElse(null);

        return UserInfoValidationContext.builder().userInfoDatabase(userInfoDatabase).build();
    }


    @Override
    public Class<? extends Validatable> supportsType() {
        return UserInfo.class;
    }

}