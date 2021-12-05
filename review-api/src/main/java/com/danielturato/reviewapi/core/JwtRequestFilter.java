package com.danielturato.reviewapi.core;

import com.danielturato.reviewapi.model.Account;
import com.danielturato.reviewapi.service.AccountService;
import com.danielturato.reviewapi.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Spring Security filter chain which filters for JWT access tokens.
 *
 * Here, this is the first filter in the chain in which a user will be authenticated if they have
 * provided a authentic JWT access token.
 *
 * @author dturato
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final int JWT_CHR_START = 7;

    private final JwtUtil jwtUtil;
    private final AccountService accountService;


    public JwtRequestFilter(JwtUtil jwtUtil, AccountService accountService) {
        this.jwtUtil = jwtUtil;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String jwt = null;
        Map<String, String> claims = new HashMap<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(JWT_CHR_START);
        }

        // If this is a JWT token and nobody is authenticated...
        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                claims = jwtUtil.verify(jwt);
            } catch (Exception ex) {
                //TODO:drt - throw invalid jwt
            }

            String accountId = claims.get("sub");
            Account account = accountService.getRawAccountById(accountId);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword(), List.of());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }


}