package com.mjw.mjwservice.validation.model.context;

import com.mjw.mjwservice.user.entity.UserInfoDb;
import lombok.Builder;

@Builder
public record UserInfoValidationContext(UserInfoDb userInfoDatabase) implements ValidationContext {


}
