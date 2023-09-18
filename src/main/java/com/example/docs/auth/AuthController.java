package com.example.docs.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
    Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @GetMapping
    public String test() {
        LOGGER.info("test ok");
        return "ok";
    }
}
