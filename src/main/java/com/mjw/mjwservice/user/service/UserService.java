package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.user.model.UserInfo;

public interface UserService {

    UserInfo registerUser(UserInfo userInfo);

    UserInfo loginUser(UserInfo userInfo);
}
