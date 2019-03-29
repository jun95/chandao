package com.selfboot.chandao.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hwj on 2019/3/29.
 */
public class UserProgressVO implements Serializable {

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
     * 预计总耗时
     */
    private Float totalEstimate;
    /**
     * 实际总耗时
     */
    private Float totalConsumed;
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

    public Float getTotalEstimate() {
        return totalEstimate;
    }

    public void setTotalEstimate(Float totalEstimate) {
        this.totalEstimate = totalEstimate;
    }

    public Float getTotalConsumed() {
        return totalConsumed;
    }

    public void setTotalConsumed(Float totalConsumed) {
        this.totalConsumed = totalConsumed;
    }
}
