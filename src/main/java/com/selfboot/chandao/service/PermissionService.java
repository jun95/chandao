package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;
import java.util.Set;

/**
 * Created by 87570 on 2019/3/18.
 */
public interface PermissionService extends BootStrapService<CdUPermission> {

    Integer selectSort(CdUPermission cdUPermission);

    void deletePermission(Long id);

    List<CdUPermission> queryResourcesListWithSelected(Integer rid);

    List<CdUPermission> loadMenu(Long userId);

    Set<String> findPermissionByUserId(Long userId);
}
