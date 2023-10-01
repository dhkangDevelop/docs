package com.example.docs.global.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusCodeEnum {
    SUCCESS(0, "", true, HttpStatus.OK),
    USER_NOT_FOUNT(10000, "user not found", false, HttpStatus.NOT_FOUND),
    PARAMETER_ERROR(400, "parameter error", false, HttpStatus.NOT_ACCEPTABLE)
    ;
    int code;
    String message;
    boolean isSuccess;

    @JsonIgnore
    HttpStatus httpStatus;


    StatusCodeEnum(int code, String message, boolean isSuccess, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
    }
}
