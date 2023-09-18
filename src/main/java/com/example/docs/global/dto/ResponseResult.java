package com.example.docs.global.dto;

import com.example.docs.global.enums.StatusCodeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    StatusCodeEnum header;

    T result;
}
