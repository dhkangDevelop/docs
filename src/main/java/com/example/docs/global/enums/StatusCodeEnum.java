package com.example.docs.global.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusCodeEnum {
    SUCCESS(0, "", true)
    ;
    int code;
    String message;
    boolean isSuccess;

    StatusCodeEnum(int code, String message, boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
    }
}
