package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdURole;
import com.selfboot.chandao.domain.CdURolePermission;
import com.selfboot.chandao.domain.CdUserRole;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by 87570 on 2019/3/18.
 */
public interface RoleService extends BootStrapService<CdURole> {


    void allotPermission(CdURolePermission rolePermission);

    List<CdURole> queryRoleListWithSelected(Integer uid);

    void addUserRole(CdUserRole userRole);
}
