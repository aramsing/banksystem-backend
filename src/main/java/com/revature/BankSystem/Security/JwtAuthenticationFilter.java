package com.revature.BankSystem.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtGenerator tokenGenerator;

    private CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter() {
    }

    @Autowired
    public JwtAuthenticationFilter(JwtGenerator tokenGenerator, CustomUserDetailService customUserDetailService) {
        this.tokenGenerator = tokenGenerator;
        this.customUserDetailService = customUserDetailService;
    }

    /**
     * Filters the incoming request to check for a valid JWT token in the Authorization header.
     * If a valid JWT token is found, it is used to authenticate the user using the {@link CustomUserDetailService}.
     *
     * If the token is valid, the user is authenticated and the user details are added to the {@link SecurityContextHolder}.
     * The request is then passed to the next filter in the chain.
     *
     * If the token is invalid or not found, the request is passed to the next filter in the chain without any authentication.
     *
     * @param request  the request to filter
     * @param response the response to filter
     * @param filterChain the chain of filters to continue with
     * @throws ServletException if there is a problem with the filter
     * @throws IOException      if there is a problem with the request or response
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFromRequest(request); // gets the JWT token from the Authorization header

        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
            String profile = tokenGenerator.getProfileUsernameFromJwt(token); // gets the username from the token
            UserDetails userDetails = customUserDetailService.loadUserByUsername(profile); // finds the user using the username
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // creates an authentication token using username, credential, and authority
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // sets the details using the request
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // sets the authentication
        }

        filterChain.doFilter(request, response); // passes the request to the next filter
    }

    /**
     * Gets the JWT token from the Authorization header of the given request.
     * The Authorization header should be in the format "Bearer <token>".
     * If the header is not found or is not in the correct format, null is returned.
     *
     * @param request the request to get the JWT token from
     * @return the JWT token if found, or null otherwise
     */
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // gets the Authorization header

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // returns the token without the "Bearer " prefix using substring(7
        }

        return null;
    }
}