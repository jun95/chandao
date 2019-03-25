package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by hwj on 2019/3/22.
 */
public interface CdTaskService extends BootStrapService<CdTask> {

    List<CdTask> selectListByProject(CdTask entity, Long id);
}
