package com.selfboot.chandao.persist;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by hwj on 2019/3/16.
 */
public class BaseEntity implements Serializable {

    /**
     * 代表删除
     */
    public static final int DEL = 0;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 是否更新，true代表更新；false代表新增
     */
    private boolean update = false;
    /**
     * 是否逻辑删除，0代表删除；1代表不删除
     */
    private Integer deleted;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
