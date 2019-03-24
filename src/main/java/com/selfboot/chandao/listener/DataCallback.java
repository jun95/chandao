package com.selfboot.chandao.listener;

import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;

import java.util.List;

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
    List<T> onPushData(CrudService crudService, DataCallbackParam<T> params);
}
