package com.selfboot.chandao.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试分析VO
 * Created by hwj on 2019/3/29.
 */
public class TestProgressVO implements Serializable {

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目开始时间
     */
    private Date projectBeginTime;
    /**
     * 项目结束时间
     */
    private Date projectEndTime;

    private Long userId;

    private String account;
    /**
     * 总测试任务数量
     */
    private Float totalTestTaskNum;
    /**
     * 完成的测试任务数量
     */
    private Float finishedTestTaskNum;
    /**
     * 项目创建人
     */
    private Long projectCreateBy;

    public Long getProjectCreateBy() {
        return projectCreateBy;
    }

    public void setProjectCreateBy(Long projectCreateBy) {
        this.projectCreateBy = projectCreateBy;
    }

    public Float getTotalTestTaskNum() {
        return totalTestTaskNum;
    }

    public void setTotalTestTaskNum(Float totalTestTaskNum) {
        this.totalTestTaskNum = totalTestTaskNum;
    }

    public Float getFinishedTestTaskNum() {
        return finishedTestTaskNum;
    }

    public void setFinishedTestTaskNum(Float finishedTestTaskNum) {
        this.finishedTestTaskNum = finishedTestTaskNum;
    }

    public Date getProjectBeginTime() {
        return projectBeginTime;
    }

    public void setProjectBeginTime(Date projectBeginTime) {
        this.projectBeginTime = projectBeginTime;
    }

    public Date getProjectEndTime() {
        return projectEndTime;
    }

    public void setProjectEndTime(Date projectEndTime) {
        this.projectEndTime = projectEndTime;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
