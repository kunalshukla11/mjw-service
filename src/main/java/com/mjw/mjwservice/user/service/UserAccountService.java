package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.common.model.LoginResponse;
import com.mjw.mjwservice.user.model.UserInfo;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {

    ResponseEntity<LoginResponse> registerUser(UserInfo userInfo);

    ResponseEntity<LoginResponse> login(UserInfo userInfo, String accessToken);

    UserInfo.UserInfoSummery getUserProfile();

    ResponseEntity<String> logout();

}
