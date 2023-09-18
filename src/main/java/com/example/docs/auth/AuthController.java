package com.example.docs.auth;

import com.example.docs.global.dto.ResponseResult;
import com.example.docs.global.enums.StatusCodeEnum;
import com.example.docs.global.utils.AESCryptoUtil;
import com.example.docs.global.utils.LogMarker;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/")
public class AuthController {
    Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AESCryptoUtil aesCryptoUtil;

    public AuthController(
        AESCryptoUtil aesCryptoUtil
    ) {
        this.aesCryptoUtil = aesCryptoUtil;
    }

    @GetMapping
    public ResponseEntity test(
            @RequestParam(name = "enc") String enc
    ) {
        LOGGER.info("test ok");
        String encrypt = "";
        String decrypt = "";
        HashMap<String, String> result = new HashMap<>();
        try {
            encrypt = aesCryptoUtil.encrypt(enc);
            decrypt = aesCryptoUtil.decrypt(encrypt);
            result.put("encrypt", encrypt);
            result.put("decrypt", decrypt);
        } catch (Exception e) {

        }

        LOGGER.info(LogMarker.SLACK.getMarker(), "{} test", Level.INFO);
        return ResponseEntity.ok()
                .body(ResponseResult.builder().header(StatusCodeEnum.SUCCESS)
                        .result(result).build());
    }
}
