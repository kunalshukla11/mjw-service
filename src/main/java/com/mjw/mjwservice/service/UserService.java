package com.mjw.mjwservice.service;

import com.mjw.mjwservice.users.UserInfo;

public interface UserService {

  UserInfo registerUser(UserInfo userInfo);

  UserInfo loginUser(UserInfo userInfo);
}
