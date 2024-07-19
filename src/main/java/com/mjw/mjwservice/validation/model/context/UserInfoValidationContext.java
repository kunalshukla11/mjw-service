package com.mjw.mjwservice.validation.model.context;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.model.UserInfo;
import lombok.Builder;

@Builder
public record UserInfoValidationContext(UserInfoDatabaseImpl userInfoDatabase) implements ValidationContext<UserInfo> {


}
