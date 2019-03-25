package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by 87570 on 2019/3/23.
 */
public interface CdBugService extends BootStrapService<CdBug> {

    List<CdBug> selectListByProject(CdBug entity, Long id);
}
