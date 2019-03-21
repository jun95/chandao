package com.selfboot.chandao.persist;

import com.selfboot.chandao.common.Pager;

import java.io.Serializable;

/**
 * 数据回调的参数
 * Created by hwj on 2019/3/21.
 */
public class DataCallbackParam<T> implements Serializable {

    private T entity;
    private Pager page;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Pager getPage() {
        return page;
    }

    public void setPage(Pager page) {
        this.page = page;
    }
}
