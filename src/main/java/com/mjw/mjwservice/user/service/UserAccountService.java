package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.security.model.ProfileResponse;
import com.mjw.mjwservice.user.model.UserInfo;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {

    ResponseEntity<ProfileResponse> registerUser(UserInfo userInfo);

    ResponseEntity<ProfileResponse> login(UserInfo userInfo, String accessToken);

    ProfileResponse getUserProfile();

    ResponseEntity<String> logout();

}
