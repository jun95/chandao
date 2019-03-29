package com.selfboot.chandao.service;

import com.selfboot.chandao.base.BaseSpringTest;
import com.selfboot.chandao.domain.CdActionLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hwj on 2019/3/29.
 */
public class ActionLogTest extends BaseSpringTest {

    @Autowired
    private CdActionLogService cdActionLogService;

    @Test
    public void testGetLatestRecord() {
        CdActionLog entity = new CdActionLog();
        entity.setObjectId(1L);
        entity.setObjectType("task");
        CdActionLog cdActionLog = cdActionLogService.selectLatestRecord(entity);
        System.out.println(cdActionLog);
    }
}
