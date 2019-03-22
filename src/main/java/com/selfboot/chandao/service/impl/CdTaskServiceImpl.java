package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdTaskDAO;
import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdTaskService;
import org.springframework.stereotype.Service;

/**
 * Created by hwj on 2019/3/22.
 */
@Service("cdTaskService")
public class CdTaskServiceImpl extends BootStrapServiceImpl<CdTask, CdTaskDAO> implements CdTaskService {

}
