package com.selfboot.chandao.exception;

/**
 * Created by hwj on 2019/3/15.
 */
public class GlobalException extends Exception {

    private String msg;

    public GlobalException(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
