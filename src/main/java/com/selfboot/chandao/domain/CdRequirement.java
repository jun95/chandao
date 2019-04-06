package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class CdRequirement extends BaseEntity {

    /** 项目id */
    @NotNull(message = "所在项目不能为空")
    private Long projectId;

    /** 项目标题 */
    private String projectName;

    /** 需求标题 */
    @NotBlank(message = "需求标题不能为空")
    private String title;

    /** 需求开始时间 */
    @NotNull(message = "开始时间不能为空")
    private Date begin;

    /** 需求结束时间 */
    @NotNull(message = "结束时间不能为空")
    private Date end;

    /** 状态；有done、doing、wait */
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

    /** 需求描述 */
    private String description;

    private String projectNameLike;

    private String requireNameLike;

    private String createNameLike;
    /**
     * 状态集
     */
    private List<String> statusList;

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getCreateNameLike() {
        return createNameLike;
    }

    public void setCreateNameLike(String createNameLike) {
        this.createNameLike = createNameLike;
    }

    public String getProjectNameLike() {
        return projectNameLike;
    }

    public void setProjectNameLike(String projectNameLike) {
        this.projectNameLike = projectNameLike;
    }

    public String getRequireNameLike() {
        return requireNameLike;
    }

    public void setRequireNameLike(String requireNameLike) {
        this.requireNameLike = requireNameLike;
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
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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