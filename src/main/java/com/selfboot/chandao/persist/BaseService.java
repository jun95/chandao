package com.selfboot.chandao.persist;

import com.github.pagehelper.PageInfo;
import com.selfboot.chandao.common.Pager;
import com.selfboot.chandao.common.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/16.
 */
public interface BaseService<V extends BaseEntity> extends RootService {

    /**
     * 保存数据（插入或更新）
     * @param entity
     * @return
     */
    ServiceResult save(List<V> entity);

    ServiceResult delete(List<V> entity);

    int delete(Long id);

    ServiceResult queryOne(V entity);

    ServiceResult queryList(V entity);

    PageInfo<V> findPage(V entity, Pager page) throws IllegalArgumentException;

    Long queryCount(V entity);

    void updateMap(Map<Object, Object> entityMap);

    /**
     * 查询最大的id
     * @return
     */
    Object selectMaxId();


}
