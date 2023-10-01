package com.example.docs.global.config;

import lombok.Data;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Data
public class RequestContext {
    Object requestData;
}
