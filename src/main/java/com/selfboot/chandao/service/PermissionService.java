package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by 87570 on 2019/3/18.
 */
public interface PermissionService extends BootStrapService<CdUPermission> {

    Integer selectSort(CdUPermission cdUPermission);

    void deletePermission(Long id);

    List<CdUPermission> queryResourcesListWithSelected(Integer rid);
}
