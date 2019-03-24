package com.selfboot.chandao.util;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.domain.CdTestTask;

import java.util.Date;

/**
 * 日志创建工具类
 * Created by 87570 on 2019/3/23.
 */
public class ActionLogHelper {

    private ActionLogHelper() {}

    /**
     * 创建任务日志
     * @param account
     * @param task 未更新前的任务
     * @param action
     * @param nowOwner 当前拥有者
     * @return
     */
    public static CdActionLog buildTaskLog(String account, CdTask task,
                                           String action,Long nowOwner) {
        CdActionLog cdActionLog = new CdActionLog();

        cdActionLog.setDate(new Date());
        cdActionLog.setProjectId(task.getProjectId());
        cdActionLog.setAccount(account);
        cdActionLog.setObjectType("task");
        cdActionLog.setObjectId(task.getId());
        cdActionLog.setAction(action);
        cdActionLog.setStatus(task.getStatus());
        cdActionLog.setPreviousOwner(task.getAssignedBy());
        cdActionLog.setAssignedBy(nowOwner);
        cdActionLog.setComment(JSON.toJSONString(task));
        cdActionLog.setDate(new Date());

        return cdActionLog;
    }

    /**
     * 创建测试任务日志
     * @param account
     * @param task 未更新前的测试任务
     * @param action
     * @param nowOwner 当前拥有者
     * @return
     */
    public static CdActionLog buildTestTaskLog(String account, CdTestTask task,
                                           String action,Long nowOwner) {
        CdActionLog cdActionLog = new CdActionLog();

        cdActionLog.setDate(new Date());
        cdActionLog.setProjectId(task.getProjectId());
        cdActionLog.setAccount(account);
        cdActionLog.setObjectType("testtask");
        cdActionLog.setObjectId(task.getId());
        cdActionLog.setAction(action);
        cdActionLog.setStatus(task.getStatus());
        cdActionLog.setPreviousOwner(task.getAssignedBy());
        cdActionLog.setAssignedBy(nowOwner);
        cdActionLog.setComment(JSON.toJSONString(task));
        cdActionLog.setDate(new Date());

        return cdActionLog;
    }

    /**
     * 创建测试任务日志
     * @param account
     * @param bug 未更新前的bug
     * @param action
     * @param nowOwner 当前拥有者
     * @return
     */
    public static CdActionLog buildBugLog(String account, CdBug bug,
                                               String action,Long nowOwner) {
        CdActionLog cdActionLog = new CdActionLog();

        cdActionLog.setDate(new Date());
        cdActionLog.setProjectId(bug.getProjectId());
        cdActionLog.setAccount(account);
        cdActionLog.setObjectType("bug");
        cdActionLog.setObjectId(bug.getId());
        cdActionLog.setAction(action);
        cdActionLog.setStatus(bug.getStatus());
        cdActionLog.setPreviousOwner(bug.getAssignedBy());
        cdActionLog.setAssignedBy(nowOwner);
        cdActionLog.setComment(JSON.toJSONString(bug));
        cdActionLog.setDate(new Date());

        return cdActionLog;
    }
}
