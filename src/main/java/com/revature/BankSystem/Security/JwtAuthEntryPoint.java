package com.revature.BankSystem.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * Called when an authentication attempt fails.
     * @param request - the request which caused the authentication exception
     * @param response - the response which should be sent back to the user
     * @param authException - the exception which was thrown during authentication
     * @throws IOException if there is a problem sending the response
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()); // sends an error if not authenticated
    }
}