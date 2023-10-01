package com.example.docs.app.v1.auth.account;

import com.example.docs.app.v1.auth.account.req.CreateAccountRequest;
import com.example.docs.app.v1.auth.account.req.GetAccountRequest;
import com.example.docs.app.v1.auth.account.res.AccountResponse;
import com.example.docs.global.dto.AccountDTO;
import com.example.docs.global.dto.ResponseResult;
import com.example.docs.global.enums.StatusCodeEnum;
import com.example.docs.global.exception.GeneralException;
import com.example.docs.global.security.CustomUserDetails;
import com.example.docs.global.security.TokenProvider;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AccountService {
    static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AccountService(
            @Autowired AccountRepository accountRepository,
            @Autowired PasswordEncoder passwordEncoder,
            @Autowired TokenProvider tokenProvider) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public void createUser(CreateAccountRequest req) {
        Account firstGeneralUser = Account.createFirstGeneralUser(req);
        String encodePassword = passwordEncoder.encode(req.getPassword());
        firstGeneralUser.setToken(encodePassword);
        Account saveUser = accountRepository.save(firstGeneralUser);
        LOGGER.info("saved Account", saveUser);
    }

    public AccountResponse getUser(GetAccountRequest req) {
        Optional<Account> optionalAccount = accountRepository.findByEmailAndIsUse(req.getEmail(), true);
        Account account = null;
        AccountDTO accountDTO = null;
        if(optionalAccount.isPresent()) {
            account = optionalAccount.get();
            accountDTO = new AccountDTO(account);
            String jwt = tokenProvider.createJwt(accountDTO);
            accountDTO.setJwt(jwt);
        } else {
            throw new GeneralException(ResponseResult.builder().header(StatusCodeEnum.USER_NOT_FOUNT).build());
        }

        return new AccountResponse(accountDTO);
    }
}
