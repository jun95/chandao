package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

public class CdUPermission extends BaseEntity {

    /** 父权限 */
    private Long parentId;

    /** 权限url */
    private String url;

    /** 权限名称 */
    private String name;

    /** 权限类型,1:菜单;2：按钮 */
    private Integer type;

    /** 排序 */
    private Integer sort;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}