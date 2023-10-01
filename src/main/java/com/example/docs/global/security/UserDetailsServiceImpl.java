package com.example.docs.global.security;

import com.example.docs.app.v1.auth.account.Account;
import com.example.docs.app.v1.auth.account.AccountRepository;
import com.example.docs.app.v1.auth.account.req.CreateAccountRequest;
import com.example.docs.app.v1.auth.account.req.GetAccountRequest;
import com.example.docs.global.dto.ResponseResult;
import com.example.docs.global.enums.StatusCodeEnum;
import com.example.docs.global.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(
        @Autowired AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = this.accountRepository.findByEmailAndIsUse(email, true);
        UserDetails userDetails = null;
        if(optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            userDetails = new CustomUserDetails(account);
        } else {
            throw new GeneralException(ResponseResult.builder().header(StatusCodeEnum.USER_NOT_FOUNT).build());
        }
        return userDetails;
    }

}
