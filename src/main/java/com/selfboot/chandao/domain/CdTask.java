package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CdTask extends BaseEntity {

    /** 父任务id */
    private Long parent;

    /** 需求id */
    @NotNull(message = "需求不能为空")
    private Long requirementId;

    /** 需求名称 */
    private String requirementName;

    /** 项目id */
    @NotNull(message = "项目不能为空")
    private Long projectId;

    /** 项目名称 */
    private String projectName;

    /** 来自哪个bug */
    private Long fromBug;

    /** 任务名称 */
    @NotBlank(message = "任务名称不能为空")
    private String name;

    /** 任务类型；1：开发 */
    private Integer type;

    /** 优先级 */
    @NotNull(message = "优先级不能为空")
    private Byte pri;

    /** 预计耗时 */
    @NotNull(message = "预计耗时不能为空")
    private Float estimate;

    /** 耗时 */
    private Float consumed;

    /** 任务状态 */
    private String status;

    /**  */
    private Long openedBy;

    /**  */
    private String openedName;

    /**  */
    private Date openedDate;

    /** 指派人id */
    private Long assignedBy;

    /** 指派人姓名 */
    private String assignedByName;

    /** 指派时间 */
    private Date assignedDate;

    /**  */
    private Long finishedBy;

    /**  */
    private String finishedName;

    /**  */
    private Date finishedDate;

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
    private String closedReason;

    /**  */
    private Long lastEditedBy;

    /**  */
    private String lastEditedName;

    /**  */
    private Date lastEditedDate;

    /**  */
    private Integer deleted;

    /** 任务描述 */
    @NotBlank(message = "任务描述不能为空")
    private String description;

    private String projectNameLike;

    private String requireNameLike;

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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName == null ? null : requirementName.trim();
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

    public Long getFromBug() {
        return fromBug;
    }

    public void setFromBug(Long fromBug) {
        this.fromBug = fromBug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Byte getPri() {
        return pri;
    }

    public void setPri(Byte pri) {
        this.pri = pri;
    }

    public Float getEstimate() {
        return estimate;
    }

    public void setEstimate(Float estimate) {
        this.estimate = estimate;
    }

    public Float getConsumed() {
        return consumed;
    }

    public void setConsumed(Float consumed) {
        this.consumed = consumed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Long getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(Long openedBy) {
        this.openedBy = openedBy;
    }

    public String getOpenedName() {
        return openedName;
    }

    public void setOpenedName(String openedName) {
        this.openedName = openedName == null ? null : openedName.trim();
    }

    public Date getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(Date openedDate) {
        this.openedDate = openedDate;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedByName() {
        return assignedByName;
    }

    public void setAssignedByName(String assignedByName) {
        this.assignedByName = assignedByName;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Long getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(Long finishedBy) {
        this.finishedBy = finishedBy;
    }

    public String getFinishedName() {
        return finishedName;
    }

    public void setFinishedName(String finishedName) {
        this.finishedName = finishedName == null ? null : finishedName.trim();
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
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

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason == null ? null : closedReason.trim();
    }

    public Long getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(Long lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public String getLastEditedName() {
        return lastEditedName;
    }

    public void setLastEditedName(String lastEditedName) {
        this.lastEditedName = lastEditedName == null ? null : lastEditedName.trim();
    }

    public Date getLastEditedDate() {
        return lastEditedDate;
    }

    public void setLastEditedDate(Date lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
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