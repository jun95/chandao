package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.io.Serializable;

public class CdURolePermission extends BaseEntity implements Serializable{

    /** ��ɫID */
    private Long rid;

    /** Ȩ��ID */
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