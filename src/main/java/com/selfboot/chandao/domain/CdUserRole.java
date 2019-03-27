package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.io.Serializable;

public class CdUserRole extends BaseEntity implements Serializable {

    /** �û�ID */
    private Long uid;

    /** ��ɫID */
    private Long rid;
    /**
     * 角色集合
     */
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}