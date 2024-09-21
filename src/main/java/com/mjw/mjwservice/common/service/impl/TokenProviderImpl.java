package com.mjw.mjwservice.common.service.impl;

import com.mjw.mjwservice.common.model.Token;
import com.mjw.mjwservice.common.service.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenProviderImpl implements TokenProvider {

    @Value("${security.jwt.secret-key}")
    private String tokenSecret;

    @Value("${security.jwt.expiration-time}")
    private long tokenExpirationMsec;


    @Override
    public Token generateAccessToken(final UserDetails userDetails) {
        return getToken(userDetails, null);
    }

    @Override
    public Token generateAccessToken(final Map<String, Object> extraClaims, final UserDetails userDetails) {
        return getToken(userDetails, extraClaims);
    }


    private Token getToken(final UserDetails userDetails, final Map<String, Object> extraClaims) {
        final Date now = new Date();
        final long duration = now.getTime() + tokenExpirationMsec;
        final Date expiryDate = new Date(duration);
        final String token = Jwts
                .builder()
                .setClaims(Optional.ofNullable(extraClaims).orElse(new HashMap<>()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(),
                ZoneId.systemDefault()));
    }


    @Override
    public String getUsernameFromToken(final String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    @Override
    public LocalDateTime getExpiryDateFromToken(final String token) {
        final Claims claims = extractAllClaims(token);
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            if (StringUtils.isNoneEmpty(token)) {
                Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parse(token);
                return true;
            }
        } catch (SignatureException ex) {
            ex.printStackTrace();
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Key getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
