package com.example.docs.app.v1.order.request;

import lombok.Data;

@Data
public class ProblemRequest {
    Integer id;

    public ProblemRequest() {
    }

    public ProblemRequest(Integer id) {
        this.id = id;
    }
}
