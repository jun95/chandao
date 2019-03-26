package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdUPermissionDAO;
import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * Created by 87570 on 2019/3/18.
 */
@Service("permissionService")
public class PermissionServiceImpl extends BootStrapServiceImpl<CdUPermission,CdUPermissionDAO>
        implements PermissionService {


    @Override
    public Integer selectSort(CdUPermission cdUPermission) {
        return targetDAO.selectSort(cdUPermission);
    }

    @Override
    public void deletePermission(Long id) {
        targetDAO.deletePermission(id);
    }
}
