package com.mjw.mjwservice.common.config;


import com.mjw.mjwservice.common.model.Token;
import com.mjw.mjwservice.common.service.TokenProvider;
import com.mjw.mjwservice.common.service.impl.CustomUserDetailsService;
import com.mjw.mjwservice.common.util.SecurityCipher;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest httpServletRequest,
                                    final @NonNull HttpServletResponse httpServletResponse,
                                    final @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String jwt = getJwtToken(httpServletRequest, true);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                final String username = tokenProvider.getUsernameFromToken(jwt);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            final String accessToken = bearerToken.substring(7);
            if (accessToken == null) {
                return null;
            }

            return SecurityCipher.decrypt(accessToken);
        }
        return null;
    }

    private String getJwtFromCookie(final HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (Token.TokenType.ACCESS.getDescription().equals(cookie.getName())) {
                final String accessToken = cookie.getValue();
                if (accessToken == null) {
                    return null;
                }

                return SecurityCipher.decrypt(accessToken);
            }
        }
        return null;
    }

    private String getJwtToken(final HttpServletRequest request, final boolean fromCookie) {
        if (fromCookie) {
            return getJwtFromCookie(request);
        }

        return getJwtFromRequest(request);
    }

}
