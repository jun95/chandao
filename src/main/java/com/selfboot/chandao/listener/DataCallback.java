package com.selfboot.chandao.listener;

import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;

import java.util.List;

/**
 * 数据回调接口
 * Created by hwj on 2019/3/21.
 */
public interface DataCallback<R /** 回调返回结果类型*/,P /** 回调入参类型*/> {

    /**
     *
     * @param crudService 需使用的service
     * @param params 执行参数
     * @return
     */
    List<R> onPushData(CrudService crudService, DataCallbackParam<P> params);
}
