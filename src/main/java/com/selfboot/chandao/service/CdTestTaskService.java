package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by 87570 on 2019/3/23.
 */
public interface CdTestTaskService extends BootStrapService<CdTestTask> {

    List<CdTestTask> selectListByProject(CdTestTask entity, Long id);
}
