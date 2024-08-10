package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.model.UserInfo;

public interface AuthenticationService {

    UserInfoDatabaseImpl registerUser(UserInfo userInfo);

    UserInfoDatabaseImpl authenticate(UserInfo userInfo);

}
