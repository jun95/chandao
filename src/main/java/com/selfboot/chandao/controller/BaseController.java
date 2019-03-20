package com.selfboot.chandao.controller;

import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.persist.BaseEntity;
import com.selfboot.chandao.persist.BootStrapService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
public class BaseController<T extends BaseEntity,S extends BootStrapService<T>> {

    @Autowired
    protected S targetService;

    protected Map<String,Object> getRecords(T t,
                                             Integer offset,
                                             Integer limit) {
        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<CdUser> rows = null;

        Map<String, Object> queryResult = targetService.selectRecord(t,offset,limit);
        if (queryResult != null) {
            rows = (List<CdUser>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }
        responseContent.put("rows", rows);
        responseContent.put("total",total);

        return responseContent;
    }

    protected <V extends BaseEntity> Map<String,Object> getRecords(BootStrapService<V> service,V v,
                                            Integer offset,
                                            Integer limit) {
        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<CdUser> rows = null;

        Map<String, Object> queryResult = service.selectRecord(v,offset,limit);
        if (queryResult != null) {
            rows = (List<CdUser>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }
        responseContent.put("rows", rows);
        responseContent.put("total",total);

        return responseContent;
    }
}
