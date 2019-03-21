package com.selfboot.chandao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/21.
 */
public enum ProjectStatusEnum {

    WAIT("wait"),DONE("done"),DOING("doing");

    private String statusName;

    ProjectStatusEnum(String statusName) {
        this.statusName = statusName;
    }

    private static Map<String,ProjectStatusEnum> resultMap = new HashMap<>();

    static {
        for (ProjectStatusEnum projectStatusEnum : ProjectStatusEnum.values()) {
            resultMap.put(projectStatusEnum.statusName,projectStatusEnum);
        }
    }

    public static ProjectStatusEnum getResponseStatus(int code) {
        return resultMap.get(code);
    }

    public String getStatusName() {
        return statusName;
    }
}
