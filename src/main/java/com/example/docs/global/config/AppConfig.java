package com.example.docs.global.config;

import com.example.docs.global.utils.AESCryptoUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class AppConfig {
    @Bean
    public AESCryptoUtil aesCryptoUtil() throws Exception{
        return new AESCryptoUtil();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    @Qualifier("customJwtParser")
    public JwtParser jwtParser(
            @Value("${jwt.secret}") String secretString,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        tokenValidityInSeconds = tokenValidityInSeconds * 1000;
        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder().setSigningKey(key).build();
    }
}
