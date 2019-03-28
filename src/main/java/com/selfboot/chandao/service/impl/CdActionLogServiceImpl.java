package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdActionLogDAO;
import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdActionLogService;
import org.springframework.stereotype.Service;

/**
 * Created by 87570 on 2019/3/23.
 */
@Service("cdActionLogService")
public class CdActionLogServiceImpl extends BootStrapServiceImpl<CdActionLog,CdActionLogDAO>
        implements CdActionLogService {


    @Override
    public CdActionLog selectLatestRecord(CdActionLog cdActionLog) {
        return targetDAO.selectLatestRecord(cdActionLog);
    }
}
