package com.selfboot.chandao.common;

import java.io.Serializable;

/**
 * Created by 87570 on 2019/3/18.
 */
public class ResponseResult<T> implements Serializable {

    private int code;
    private T result;
    private String message;

    public ResponseResult() {}

    public ResponseResult(ResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setResponseStatus(ResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
