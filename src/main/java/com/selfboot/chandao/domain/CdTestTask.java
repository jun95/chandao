package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CdTestTask extends BaseEntity {

    /**  */
    @NotBlank(message = "测试任务名称不能为空")
    private String name;

    /**  */
    @NotNull(message = "项目不能为空")
    private Long projectId;

    /** 项目标题 */
    private String projectName;

    /**  */
    @NotNull(message = "需求不能为空")
    private Long requirementId;

    /** 需求名称 */
    private String requirementName;

    /** 测试任务创建人 */
    private String owner;

    /**  */
    @NotNull(message = "开始时间不能为空")
    private Date begin;

    /**  */
    @NotNull(message = "结束时间不能为空")
    private Date end;

    /**  */
    private String status;

    /**  */
    private Long openedBy;

    /**  */
    private String openedName;

    /**  */
    private Date openedDate;

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
    private String closedReason;

    /**  */
    private Long assignedBy;

    /**  */
    private String assignedByName;

    /**  */
    private Date assignedDate;

    /**  */
    private Integer deleted;

    /**  */
    private String description;

    private String projectNameLike;

    private String requireNameLike;

    private String testTaskNameLike;

    private String statusDesc;

    private String assignedByNameLike;

    public String getAssignedByNameLike() {
        return assignedByNameLike;
    }

    public void setAssignedByNameLike(String assignedByNameLike) {
        this.assignedByNameLike = assignedByNameLike;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    public String getTestTaskNameLike() {
        return testTaskNameLike;
    }

    public void setTestTaskNameLike(String testTaskNameLike) {
        this.testTaskNameLike = testTaskNameLike;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
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

    public String getClosedReason() {
        return closedReason;
    }

    public void setClosedReason(String closedReason) {
        this.closedReason = closedReason == null ? null : closedReason.trim();
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