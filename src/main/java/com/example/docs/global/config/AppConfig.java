package com.example.docs.global.config;

import com.example.docs.global.utils.AESCryptoUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@Configuration
public class AppConfig {
    @Bean
    public AESCryptoUtil aesCryptoUtil() throws Exception{
        return new AESCryptoUtil();
    }
}
