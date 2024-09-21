package com.mjw.mjwservice.common.util;

import com.mjw.mjwservice.common.model.Token;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public HttpCookie createAccessTokenCookie(final String token, final Long duration) {
        final String encryptedToken = SecurityCipher.encrypt(token);
        return ResponseCookie.from(Token.TokenType.ACCESS.getDescription(), encryptedToken)
                .maxAge(duration)
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .path("/")
                .build();
    }


    public HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(Token.TokenType.ACCESS.getDescription(), "")
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .secure(true)
                .sameSite(Cookie.SameSite.NONE.attributeValue())
                .build();
    }

}
