package com.example.docs.app.v1.auth.account.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccountRequest extends AccountRequest {
    @NotNull
    @NotEmpty
    String password;

    public CreateAccountRequest() {

    }

    public CreateAccountRequest(String email){
        super(email);
    }

    public CreateAccountRequest(String email, String password) {
        super(email);
        this.password = password;
    }
}
