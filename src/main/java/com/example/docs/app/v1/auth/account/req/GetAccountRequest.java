package com.example.docs.app.v1.auth.account.req;

import lombok.Data;

@Data
public class GetAccountRequest extends AccountRequest {

    public GetAccountRequest(String email) {
        super(email);
    }
}
