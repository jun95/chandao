package com.selfboot.chandao.vo;

import java.io.Serializable;

/**
 * 项目进度vo
 * Created by 87570 on 2019/3/28.
 */
public class ProjectProgressVO implements Serializable {

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 总需求数
     */
    private Integer totalReqNum;
    /**
     * 完成需求数
     */
    private Integer finishReqNum;

    /**
     * 总任务数
     */
    private Integer totalTaskNum;
    /**
     * 完成任务数
     */
    private Integer finishTaskNum;
    /**
     * 关闭任务数
     */
    private Integer closedTaskNum;

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

    public Integer getTotalReqNum() {
        return totalReqNum;
    }

    public void setTotalReqNum(Integer totalReqNum) {
        this.totalReqNum = totalReqNum;
    }

    public Integer getFinishReqNum() {
        return finishReqNum;
    }

    public void setFinishReqNum(Integer finishReqNum) {
        this.finishReqNum = finishReqNum;
    }

    public Integer getTotalTaskNum() {
        return totalTaskNum;
    }

    public void setTotalTaskNum(Integer totalTaskNum) {
        this.totalTaskNum = totalTaskNum;
    }

    public Integer getFinishTaskNum() {
        return finishTaskNum;
    }

    public void setFinishTaskNum(Integer finishTaskNum) {
        this.finishTaskNum = finishTaskNum;
    }

    public Integer getClosedTaskNum() {
        return closedTaskNum;
    }

    public void setClosedTaskNum(Integer closedTaskNum) {
        this.closedTaskNum = closedTaskNum;
    }
}
