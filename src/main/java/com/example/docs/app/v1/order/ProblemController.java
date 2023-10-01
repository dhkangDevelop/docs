package com.example.docs.app.v1.order;

import com.example.docs.app.v1.order.request.ProblemRequest;
import com.example.docs.app.v1.order.request.ProblemSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/v1/problem")
    public String request(ProblemRequest req) {
//        problemService.save(orderRequest);
        return "ok";
    }
}
