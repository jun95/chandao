package com.selfboot.chandao.vo;

import java.io.Serializable;

/**
 * 用户分析查询VO
 * Created by hwj on 2019/3/29.
 */
public class UserProgressQueryVO implements Serializable {

    /**
     * 项目名称
     */
    private String projectNameLike;

    private String accountLike;

    public String getProjectNameLike() {
        return projectNameLike;
    }

    public void setProjectNameLike(String projectNameLike) {
        this.projectNameLike = projectNameLike;
    }

    public String getAccountLike() {
        return accountLike;
    }

    public void setAccountLike(String accountLike) {
        this.accountLike = accountLike;
    }
}
