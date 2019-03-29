package com.selfboot.chandao.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试分析VO
 * Created by hwj on 2019/3/29.
 */
public class BugProgressVO implements Serializable {

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * bug数
     */
    private Integer totalBugNum;
    /**
     * 重复修复率
     */
    private String repeatRepairRate;

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
        this.projectName = projectName;
    }

    public Integer getTotalBugNum() {
        return totalBugNum;
    }

    public void setTotalBugNum(Integer totalBugNum) {
        this.totalBugNum = totalBugNum;
    }

    public String getRepeatRepairRate() {
        return repeatRepairRate;
    }

    public void setRepeatRepairRate(String repeatRepairRate) {
        this.repeatRepairRate = repeatRepairRate;
    }
}
