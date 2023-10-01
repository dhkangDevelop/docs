package com.example.docs.app.v1.auth.account;

import com.example.docs.app.v1.auth.account.req.CreateAccountRequest;
import com.example.docs.app.v1.auth.account.req.GetAccountRequest;
import com.example.docs.app.v1.auth.account.res.AccountResponse;
import com.example.docs.global.dto.ResponseResult;
import com.example.docs.global.enums.StatusCodeEnum;
import com.example.docs.global.exception.GeneralException;
import com.example.docs.global.utils.AESCryptoUtil;
import com.example.docs.global.utils.LogMarker;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/")
public class AccountController {
    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AESCryptoUtil aesCryptoUtil;
    private final AccountService accountService;

    public AccountController(
        @Autowired AESCryptoUtil aesCryptoUtil,
        @Autowired AccountService accountService
    ) {
        this.aesCryptoUtil = aesCryptoUtil;
        this.accountService = accountService;
    }

    @GetMapping("/v1/user")
    public ResponseEntity getUser(
            @Valid GetAccountRequest req,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new GeneralException(ResponseResult.builder().header(StatusCodeEnum.PARAMETER_ERROR).build());
        }

        AccountResponse res = accountService.getUser(req);

        return ResponseEntity.ok().header("Authorization", "Bearer " + res.getJwt())
                .body(ResponseResult.builder().header(StatusCodeEnum.SUCCESS).result(res).build());
    }

    @PostMapping("/v1/user")
    public ResponseEntity createUser(
            @RequestBody @Valid CreateAccountRequest req,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new GeneralException(ResponseResult.builder().header(StatusCodeEnum.PARAMETER_ERROR).build());
        }

        accountService.createUser(req);

        LOGGER.info(LogMarker.SLACK.getMarker(), "{} test", Level.INFO);
        return ResponseEntity.ok()
                .body(ResponseResult.builder().header(StatusCodeEnum.SUCCESS).build());
    }

    @GetMapping("/test")
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
