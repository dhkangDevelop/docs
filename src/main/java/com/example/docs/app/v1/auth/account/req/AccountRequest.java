package com.example.docs.app.v1.auth.account.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {

    @Email
    @NotEmpty
    @NotNull
    String email;

    public AccountRequest() {

    }

    public AccountRequest(String email) {
        this.email = email;
    }
}
