package com.example.docs.app.v1.order;

import com.example.docs.app.v1.order.request.ProblemRequest;
import com.example.docs.app.v1.order.request.ProblemSaveRequest;
import com.example.docs.global.annotation.Retry;
import com.example.docs.global.annotation.Trace;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    @Trace
    @Retry
    @Transactional
    public void save(ProblemSaveRequest req) {
        // 저장 로직
        if(req.getName().equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        Problem problem = Problem.createOrder(req);
        problemRepository.save(problem);
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
