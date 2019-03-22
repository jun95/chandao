package com.selfboot.chandao.controller;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.BaseEntity;
import com.selfboot.chandao.persist.BootStrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
public class BaseController<T extends BaseEntity,S extends BootStrapService<T>> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected S targetService;

    protected <V extends BaseEntity> Map<String,Object> getRecords(BootStrapService<V> service,V v,
                                                                   Integer offset,
                                                                   Integer limit,
                                                                   DataCallback<List<V>> callback) {
        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<CdUser> rows = null;
        Map<String, Object> queryResult = null;
        if (callback == null) {
            queryResult = service.selectRecord(v,offset,limit);
        } else {
            service.selectRecord(v,offset,limit,callback);
        }

        logger.info("查询出的结果为：{}", JSON.toJSONString(queryResult));

        if (queryResult != null) {
            rows = (List<CdUser>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }
        responseContent.put("rows", rows);
        responseContent.put("total",total);

        return responseContent;
    }

    /**
     * 继承BaseController中泛型service类自身的调用
     * @param t
     * @param offset
     * @param limit
     * @return
     */
    protected Map<String,Object> getRecords(T t,
                                             Integer offset,
                                             Integer limit) {
        return getRecords(t,offset,limit,null);
    }

    protected Map<String,Object> getRecords(T t,
                                            Integer offset,
                                            Integer limit, DataCallback<List<T>> callback) {

        return getRecords(targetService,t,offset,limit,callback);
    }

    /**
     * 任意service类自身的调用
     * @param service
     * @param v
     * @param offset
     * @param limit
     * @param <V>
     * @return
     */
    protected <V extends BaseEntity> Map<String,Object> getRecords(BootStrapService<V> service,V v,
                                            Integer offset,
                                            Integer limit) {
        return getRecords(service,v,offset,limit,null);
    }
}
