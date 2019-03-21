package com.selfboot.chandao.common;

import java.io.Serializable;

/**
 * Created by 87570 on 2019/3/16.
 */
public class Pager<T> implements Serializable {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 页大小
     */
    private int rows;

    private String orderBy;

    private String sort;

    private Integer offset;

    private Integer limit;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
