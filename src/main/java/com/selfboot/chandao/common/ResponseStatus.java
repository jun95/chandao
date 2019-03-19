package com.selfboot.chandao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/18.
 */
public enum ResponseStatus {

    OK(200,"OK"),
    ERROR(500,"系统异常"),
    ILLEGAL_ARG(100,"參數非法");

    private int code;
    private String message;

    private static Map<Integer,ResponseStatus> resultMap = new HashMap<>();

    static {
        for (ResponseStatus responseStatus : ResponseStatus.values()) {
            resultMap.put(responseStatus.code,responseStatus);
        }
    }

    public static ResponseStatus getResponseStatus(int code) {
        return resultMap.get(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResponseStatus(int code,String message) {
        this.code = code;
        this.message = message;
    }
}
