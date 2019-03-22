package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by hwj on 2019/3/21.
 */
public interface CdProjectService extends BootStrapService<CdProject> {

    /**
     * 查询状态为待开始、进行中的项目
     * @param cdProject
     * @return
     */
    List<CdProject> selectUnCloseProject(CdProject cdProject);
}
