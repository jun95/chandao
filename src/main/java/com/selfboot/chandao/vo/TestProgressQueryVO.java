package com.selfboot.chandao.vo;

import java.io.Serializable;

/**
 * 测试分析查询VO
 * Created by hwj on 2019/3/29.
 */
public class TestProgressQueryVO implements Serializable {

    /**
     * 项目名称
     */
    private String projectName;

    private String account;
    /**
     * 项目创建人
     */
    private Long projectCreateBy;

    public Long getProjectCreateBy() {
        return projectCreateBy;
    }

    public void setProjectCreateBy(Long projectCreateBy) {
        this.projectCreateBy = projectCreateBy;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
