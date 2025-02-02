package com.mjw.mjwservice.security.service;


import com.mjw.mjwservice.security.model.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public interface TokenProvider {

    Token generateAccessToken(UserDetails subject);

    Token generateAccessToken(Map<String, Object> extraClaims, UserDetails subject);


    String getUsernameFromToken(String token);

    LocalDateTime getExpiryDateFromToken(String token);

    boolean validateToken(String token);

}
