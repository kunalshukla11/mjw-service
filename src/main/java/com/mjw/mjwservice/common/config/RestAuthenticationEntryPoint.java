package com.mjw.mjwservice.common.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //TODO Search more about this and implement it better
    @Override
    public void commence(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
                         final AuthenticationException e) throws IOException, ServletException, IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getLocalizedMessage());
    }

}
