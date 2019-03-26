package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class CdURole extends BaseEntity {


    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /** 角色描述 */
    private String roleDesc;

    /** 创建时间 */
    private Date createTime;

    private String nameLike;

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}