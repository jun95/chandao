package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.util.Date;

public class CdProject extends BaseEntity {
    /**  */
    private Long groupId;

    /**  */
    private String groupName;

    /** 项目名称 */
    private String name;

    /** 项目开始时间 */
    private Date begin;

    /** 项目结束时间 */
    private Date end;

    /** 项目耗时，以天为单位 */
    private Short days;

    /** 项目状态；有done、wait */
    private String status;

    /**  */
    private Long createBy;

    /**  */
    private String createName;

    /**  */
    private Date createDate;

    /**  */
    private Long closedBy;

    /**  */
    private String closedName;

    /**  */
    private Date closedDate;

    /**  */
    private Long canceledBy;

    /**  */
    private String canceledName;

    /**  */
    private Date canceledDate;

    /**  */
    private Integer deleted;

    /** 项目描述 */
    private String description;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Short getDays() {
        return days;
    }

    public void setDays(Short days) {
        this.days = days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(Long closedBy) {
        this.closedBy = closedBy;
    }

    public String getClosedName() {
        return closedName;
    }

    public void setClosedName(String closedName) {
        this.closedName = closedName == null ? null : closedName.trim();
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Long getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(Long canceledBy) {
        this.canceledBy = canceledBy;
    }

    public String getCanceledName() {
        return canceledName;
    }

    public void setCanceledName(String canceledName) {
        this.canceledName = canceledName == null ? null : canceledName.trim();
    }

    public Date getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(Date canceledDate) {
        this.canceledDate = canceledDate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}