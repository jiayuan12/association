package com.feng.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult{
    private int code;
    private String message;
    private Object data;

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(int code, Object data) {
        this.code = code;
        this.data = data;
    }
    public ResponseResult(Object data) {
       this(200,data);
    }
}
