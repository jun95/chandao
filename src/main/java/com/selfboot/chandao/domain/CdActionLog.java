package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.util.Date;

public class CdActionLog extends BaseEntity {

    /** task,testtask,bug */
    private String objectType;

    /**  */
    private Long objectId;

    /**  */
    private Long projectId;

    /**  */
    private String action;

    /**  */
    private String status;

    /** 曾经拥有者 */
    private Long previousOwner;

    /** 当前拥有者 */
    private Long assignedBy;

    /** 当前拥有者账号 */
    private String account;

    /** 创建日期 */
    private Date date;

    /**  */
    private String comment;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Long getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(Long previousOwner) {
        this.previousOwner = previousOwner;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}