package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CdBug extends BaseEntity {

    /**  */
    @NotNull(message = "项目不能为空")
    private Long projectId;

    /** 项目标题 */
    private String projectName;

    /**  */
    private Long taskId;

    /**  */
    @NotBlank(message = "BUG标题不能为空")
    private String title;

    /** 关键词 */
    @NotBlank(message = "关键词不能为空")
    private String keywords;

    /** 严重性 */
    private Byte severity;

    /** 优先级 */
    @NotNull(message = "优先级不能为空")
    private Byte pri;

    /**  */
    private String status;

    /**  */
    private Short activatedCount;

    /**  */
    private Date activatedDate;

    /**  */
    private Long openedBy;

    /**  */
    private String openedName;

    /**  */
    private Date openedDate;

    /**  */
    private Long assignedBy;

    /**  */
    private String assignedByName;

    /**  */
    private Date assignedDate;

    /**  */
    private Long resolvedBy;

    /**  */
    private String resolvedName;

    /**  */
    private String resolution;

    /**  */
    private Date resolvedDate;

    /**  */
    private Long closedBy;

    /**  */
    private String closedName;

    /**  */
    private Date closedDate;

    /**  */
    private Integer duplicateBug;

    /**  */
    private String linkBug;

    /**  */
    private Long testtaskId;

    /**  */
    private Long lastEditedBy;

    /**  */
    private String lastEditedName;

    /**  */
    private Date lastEditedDate;

    /**  */
    private Integer deleted;

    /** 重现步骤 */
    @NotBlank(message = "重现步骤不能为空")
    private String steps;

    private String titleLike;

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

    public String getTitleLike() {
        return titleLike;
    }

    public void setTitleLike(String titleLike) {
        this.titleLike = titleLike;
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Byte getSeverity() {
        return severity;
    }

    public void setSeverity(Byte severity) {
        this.severity = severity;
    }

    public Byte getPri() {
        return pri;
    }

    public void setPri(Byte pri) {
        this.pri = pri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Short getActivatedCount() {
        return activatedCount;
    }

    public void setActivatedCount(Short activatedCount) {
        this.activatedCount = activatedCount;
    }

    public Date getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Date activatedDate) {
        this.activatedDate = activatedDate;
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

    public Long getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(Long resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public String getResolvedName() {
        return resolvedName;
    }

    public void setResolvedName(String resolvedName) {
        this.resolvedName = resolvedName == null ? null : resolvedName.trim();
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution == null ? null : resolution.trim();
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
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

    public Integer getDuplicateBug() {
        return duplicateBug;
    }

    public void setDuplicateBug(Integer duplicateBug) {
        this.duplicateBug = duplicateBug;
    }

    public String getLinkBug() {
        return linkBug;
    }

    public void setLinkBug(String linkBug) {
        this.linkBug = linkBug == null ? null : linkBug.trim();
    }

    public Long getTesttaskId() {
        return testtaskId;
    }

    public void setTesttaskId(Long testtaskId) {
        this.testtaskId = testtaskId;
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

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps == null ? null : steps.trim();
    }
}