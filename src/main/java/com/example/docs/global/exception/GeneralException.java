package com.example.docs.global.exception;

import com.example.docs.global.dto.ResponseResult;
import lombok.Data;

@Data
public class GeneralException extends RuntimeException {
    ResponseResult responseResult;

    public GeneralException() {
        super();
    }

    public GeneralException(ResponseResult responseResult) {
        super();
        this.responseResult = responseResult;
    }

    public GeneralException(ResponseResult responseResult, String message) {
        super(message);
        this.responseResult = responseResult;
    }

    public GeneralException(ResponseResult responseResult, String message, Throwable cause) {
        super(message, cause);
        this.responseResult = responseResult;
    }

    public GeneralException(ResponseResult responseResult, Throwable cause) {
        super(cause);
        this.responseResult = responseResult;
    }

    protected GeneralException(ResponseResult responseResult, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.responseResult = responseResult;
    }
}
