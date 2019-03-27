package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CdUPermission extends BaseEntity {

    /** 父权限 */
    //@NotNull(message = "父权限不能为空")
    private Long parentId;

    /** 权限url */
    @NotBlank(message = "权限链接不能为空")
    private String url;

    /** 权限名称 */
    @NotBlank(message = "权限名称不能为空")
    private String name;

    /** 权限类型,1:菜单;2：按钮 */
    @NotNull(message = "权限类型不能为空")
    private Integer type;

    /** 排序 */
    private Integer sort;

    private String nameLike;

    private String checked;//是否选中

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

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