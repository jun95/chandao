package com.selfboot.chandao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/21.
 */
public enum TaskStatusEnum {
    //待开始 已关闭 进行中 已完成
    WAIT("wait"),DONE("done"),DOING("doing"),PAUSE("pause"),CANCEL("cancel"),CLOSED("closed");

    private String statusName;

    TaskStatusEnum(String statusName) {
        this.statusName = statusName;
    }

    private static Map<String, TaskStatusEnum> resultMap = new HashMap<>();

    static {
        for (TaskStatusEnum taskStatusEnum : TaskStatusEnum.values()) {
            resultMap.put(taskStatusEnum.statusName,taskStatusEnum);
        }
    }

    public static TaskStatusEnum getStatus(String statusName) {
        return resultMap.get(statusName);
    }

    public String getStatusName() {
        return statusName;
    }
}
