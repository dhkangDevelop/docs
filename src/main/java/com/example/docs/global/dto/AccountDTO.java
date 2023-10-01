package com.example.docs.global.dto;

import com.example.docs.app.v1.auth.account.Account;
import lombok.Data;

@Data
public class AccountDTO {
    String email;
    String jwt;

    public AccountDTO(Account account) {
        this.email = account.getEmail();
        this.jwt = null;
    }
}
