package com.selfboot.chandao.common;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87570 on 2019/3/16.
 */
public class ServiceResult<T> implements Serializable {

    private T result;
    private String returnMessage;
    private List<String> errorMessage = new ArrayList<>(5);

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public void addError(String errorMsg) {
        errorMessage.add(errorMsg);
    }

    public boolean isSuccess() {
        return CollectionUtils.isEmpty(errorMessage);
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
