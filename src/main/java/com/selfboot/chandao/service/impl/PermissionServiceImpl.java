package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdUPermissionDAO;
import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.PermissionService;
import com.selfboot.chandao.util.MenuUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    @Override
    public List<CdUPermission> queryResourcesListWithSelected(Integer rid) {
        return targetDAO.queryResourcesListWithSelected(rid);
    }

    @Override
    public List<CdUPermission> loadMenu(Long userId) {
        return MenuUtil.getMenu(targetDAO.getMenu(userId,1));
    }

    @Override
    public Set<String> findPermissionByUserId(Long userId) {
        return targetDAO.findPermissionByUserId(userId);
    }
}
