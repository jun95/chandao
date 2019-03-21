package com.selfboot.chandao.persist;

import com.selfboot.chandao.listener.DataCallback;

import java.util.List;
import java.util.Map;

/**
 * 支持bootstrap的相关service
 * Created by hwj on 2019/3/20.
 */
public interface BootStrapService<T extends BaseEntity> extends BaseService<T> {

    Map<String, Object> selectRecord(T t, Integer offset, Integer limit);

    Map<String, Object> selectRecord(T t, Integer offset, Integer limit, DataCallback<List<T>> callback);

}
