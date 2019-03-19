package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import java.io.Serializable;

public class CdUserRole extends BaseEntity implements Serializable {

    /** ÓÃ»§ID */
    private Long uid;

    /** ½ÇÉ«ID */
    private Long rid;


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