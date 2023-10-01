package com.example.docs.app.v1.auth.account.res;

import com.example.docs.global.dto.AccountDTO;
import lombok.Data;

@Data
public class AccountResponse{
    String email;
    String jwt;

    public AccountResponse(AccountDTO dto) {
        this.email = dto.getEmail();
        this.jwt = dto.getJwt();
    }
}
