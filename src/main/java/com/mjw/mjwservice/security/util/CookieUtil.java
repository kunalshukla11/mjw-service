package com.mjw.mjwservice.security.util;

import com.mjw.mjwservice.security.model.Token;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {


    //TODO Set secure = true and sameSite = None for production
    public HttpCookie createAccessTokenCookie(final String token, final Long duration) {
        final String encryptedToken = SecurityCipher.encrypt(token);
        return ResponseCookie.from(Token.TokenType.ACCESS.getDescription(), encryptedToken)
                .maxAge(duration)
                .httpOnly(true)
                .secure(false)
                .sameSite(Cookie.SameSite.LAX.attributeValue())
                .path("/")
                .build();
    }


    public HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(Token.TokenType.ACCESS.getDescription(), "")
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .secure(false)
                .sameSite(Cookie.SameSite.LAX.attributeValue())
                .build();
    }

}
