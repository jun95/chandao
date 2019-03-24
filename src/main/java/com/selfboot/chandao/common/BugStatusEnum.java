package com.selfboot.chandao.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/21.
 */
public enum BugStatusEnum {
    //被激活 已解决 已关闭
    ACTIVE("active"),RESOLVED("resolved"),CLOSED("closed");

    private String statusName;

    BugStatusEnum(String statusName) {
        this.statusName = statusName;
    }

    private static Map<String,BugStatusEnum> resultMap = new HashMap<>();

    static {
        for (BugStatusEnum bugStatusEnum : BugStatusEnum.values()) {
            resultMap.put(bugStatusEnum.statusName,bugStatusEnum);
        }
    }

    public static BugStatusEnum getStatus(String statusName) {
        return resultMap.get(statusName);
    }

    public String getStatusName() {
        return statusName;
    }
}
