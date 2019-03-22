package com.selfboot.chandao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/21.
 */
public enum ProjectStatusEnum {
    //待开始 已关闭 进行中 已完成
    WAIT("wait"),DONE("done"),DOING("doing"),FINISHED("finished");

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

    public static ProjectStatusEnum getStatus(String statusName) {
        return resultMap.get(statusName);
    }

    public String getStatusName() {
        return statusName;
    }
}
