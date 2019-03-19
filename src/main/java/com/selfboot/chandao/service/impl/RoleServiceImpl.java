package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdURoleDAO;
import com.selfboot.chandao.dao.CdURolePermissionDAO;
import com.selfboot.chandao.dao.CdUserDAO;
import com.selfboot.chandao.domain.CdURole;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 87570 on 2019/3/18.
 */
@Service("roleService")
public class RoleServiceImpl extends CrudService<CdURole,CdURoleDAO> implements RoleService {

    @Resource
    private CdURolePermissionDAO cdURolePermissionDAO;

    @Resource
    private CdUserDAO cdUserDAO;


}
