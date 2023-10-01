package com.example.docs.global.security;

import com.example.docs.app.v1.auth.account.AccountRepository;
import io.jsonwebtoken.*;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.LinkedHashMap;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
    private final TokenProvider tokenProvider;
    private final AccountRepository accountRepository;

    public JwtTokenFilter(
        @Autowired TokenProvider tokenProvider,
        @Autowired AccountRepository accountRepository) {
        this.tokenProvider = tokenProvider;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtil.isNullOrEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // GET JWT Token
        final String jwt = header.substring(header.lastIndexOf("Bearer ")).split(" ")[1];
        if(!tokenProvider.isValid(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = tokenProvider.getUserDetails(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
