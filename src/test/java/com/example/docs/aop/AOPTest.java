package com.example.docs.aop;

import com.example.docs.DocsApplication;
import com.example.docs.app.v1.order.ProblemService;
import com.example.docs.app.v1.order.request.ProblemRequest;
import com.example.docs.app.v1.order.request.ProblemSaveRequest;
import org.junit.jupiter.api.Test;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(DocsApplication.class)
@SpringBootTest
public class AOPTest {
    static final Logger LOGGER = LoggerFactory.getLogger(AOPTest.class);
    @Autowired
    ProblemService problemService;

    @Test
    void test() {
        for (int i=0;i<5;i++) {
            LOGGER.info("client request i={}", i);
            ProblemSaveRequest request = new ProblemSaveRequest(i, "test" + i, i);
            problemService.save(request);
        }
    }
}
