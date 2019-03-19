package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.io.Serializable;

public class CdURolePermission extends BaseEntity implements Serializable{

    /** ½ÇÉ«ID */
    private Long rid;

    /** È¨ÏÞID */
    private Long pid;


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