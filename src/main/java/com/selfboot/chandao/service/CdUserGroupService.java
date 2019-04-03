package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdUserGroup;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by 87570 on 2019/3/20.
 */
public interface CdUserGroupService extends BootStrapService<CdUserGroup> {

    List<CdUserGroup> getListByProjectId(Long projectId);

    List<CdUserGroup> getListByConditionWithRole(CdUserGroup cdUserGroup);
}
