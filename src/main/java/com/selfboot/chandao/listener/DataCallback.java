package com.selfboot.chandao.listener;

import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;

/**
 * 数据回调接口
 * Created by hwj on 2019/3/21.
 */
public interface DataCallback<T> {

    /**
     *
     * @param crudService 需使用的service
     * @param params 执行参数
     * @return
     */
    <V> T onPushData(CrudService crudService, DataCallbackParam<V> params);
}
