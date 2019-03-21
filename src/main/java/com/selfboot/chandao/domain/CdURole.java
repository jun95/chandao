package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CdURole extends BaseEntity implements Serializable{

    /** ��ɫ���� */
    @NotBlank
    private String name;

    /** ��ɫ���� */
    @NotNull
    private Integer type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}