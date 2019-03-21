package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class CdGroup extends BaseEntity {

    /**  */
    @NotBlank(message = "项目组名称不能为空")
    private String name;

    /**  */
    private String description;

    /**  */
    private Long createBy;

    /**  */
    private String createName;

    /**  */
    private Date createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}