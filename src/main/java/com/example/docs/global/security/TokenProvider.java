package com.example.docs.global.security;

import com.example.docs.global.dto.AccountDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class TokenProvider {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final JwtParser jwtParser;
    private final String sesecretString;
    private final ObjectMapper objectMapper;
    private final SecretKey key;
    private final long tokenValidityInSeconds;
    private final UserDetailsServiceImpl userDetailsService;

    public TokenProvider(
        @Autowired @Qualifier("customJwtParser") JwtParser jwtParser,
        @Value("${jwt.secret}") String secretString,
        @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
        @Autowired UserDetailsServiceImpl userDetailsService,
        @Autowired ObjectMapper objectMapper
    ) {
        this.jwtParser = jwtParser;
        this.sesecretString = secretString;
        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInSeconds = tokenValidityInSeconds*1000;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    public boolean isValid(String jwt) {
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);
        Header jwtHeader = claimsJws.getHeader();
        Claims body = claimsJws.getBody();
        return body.getExpiration().after(new Date());
    }

    public Authentication getUserDetails(String jwt) {
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);

        Claims body = claimsJws.getBody();
        String email = body.getSubject();
        LinkedHashMap authorization = body.get("Authorization", LinkedHashMap.class);

        List<SimpleGrantedAuthority> authorities = ((ArrayList<LinkedHashMap>)authorization.get("authorities")).stream().map(m->new SimpleGrantedAuthority(m.get("authority").toString())).collect(Collectors.toList());
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(email, userDetails, authorities);
    }

    public String createJwt(AccountDTO accountDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(accountDTO.getEmail());
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInSeconds);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(accountDTO.getEmail(), userDetails, userDetails.getAuthorities());
        return Jwts.builder().signWith(key).setSubject(accountDTO.getEmail())
                .claim("Authorization", usernamePasswordAuthenticationToken).setExpiration(validity).compact();
    }

}
