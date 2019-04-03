package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdUserGroupDAO;
import com.selfboot.chandao.domain.CdUserGroup;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdUserGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87570 on 2019/3/20.
 */
@Service("cdUserGroupService")
public class CdUserGroupServiceImpl extends BootStrapServiceImpl<CdUserGroup,CdUserGroupDAO>
        implements CdUserGroupService {


    @Override
    public List<CdUserGroup> getListByProjectId(Long projectId) {

        return targetDAO.getListByProjectId(projectId);
    }

    @Override
    public List<CdUserGroup> getListByConditionWithRole(CdUserGroup cdUserGroup) {
        return targetDAO.selectListByConditionWithRole(cdUserGroup);
    }
}
