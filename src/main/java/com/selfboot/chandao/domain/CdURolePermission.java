package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CdURolePermission extends BaseEntity implements Serializable{

    /** ��ɫID */
    @NotNull(message = "角色不能为空")
    private Long rid;

    /** Ȩ��ID */
    private Long pid;
    /**
     * 权限id集
     */
    private String resourcesId;

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}