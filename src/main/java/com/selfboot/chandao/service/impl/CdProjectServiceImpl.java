package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdProjectDAO;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hwj on 2019/3/21.
 */
@Service("cdProjectService")
public class CdProjectServiceImpl extends BootStrapServiceImpl<CdProject, CdProjectDAO> implements CdProjectService {


    @Override
    public List<CdProject> selectUnCloseProject(CdProject cdProject, Long userId) {
        return targetDAO.selectUnCloseProject(cdProject,userId);
    }

    @Override
    public List<CdProject> selectListByGroup(CdProject entity, Long userId) {
        return targetDAO.selectListByGroup(entity,userId);
    }
}
