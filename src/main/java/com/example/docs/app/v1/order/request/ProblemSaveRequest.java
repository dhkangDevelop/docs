package com.example.docs.app.v1.order.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProblemSaveRequest extends ProblemRequest {


    String name;
    Integer cnt;

    public ProblemSaveRequest() {
    }

    public ProblemSaveRequest(Integer id) {
        super(id);
    }

    public ProblemSaveRequest(Integer id, String name, Integer cnt) {
        super(id);
        this.name = name;
        this.cnt = cnt;
    }
}
