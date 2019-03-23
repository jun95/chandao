package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdTestTaskDAO;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdTestTaskService;
import org.springframework.stereotype.Service;

/**
 * Created by 87570 on 2019/3/23.
 */
@Service("cdTestTaskService")
public class CdTestTaskServiceImpl extends BootStrapServiceImpl<CdTestTask,CdTestTaskDAO>
        implements CdTestTaskService {


}
