package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.util.Date;

public class CdUserGroup extends BaseEntity {
    /**  */
    private Long userId;

    /**  */
    private String account;

    /**  */
    private Long group;

    /**  */
    private Date createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}