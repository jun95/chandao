package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdURoleDAO;
import com.selfboot.chandao.dao.CdURolePermissionDAO;
import com.selfboot.chandao.dao.CdUserRoleDAO;
import com.selfboot.chandao.domain.CdURole;
import com.selfboot.chandao.domain.CdURolePermission;
import com.selfboot.chandao.domain.CdUserRole;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by 87570 on 2019/3/18.
 */
@Service("roleService")
public class RoleServiceImpl extends BootStrapServiceImpl<CdURole,CdURoleDAO> implements RoleService {

    @Resource
    private CdURolePermissionDAO cdURolePermissionDAO;

    @Resource
    private CdUserRoleDAO cdUserRoleDAO;

    /*@Resource
    private CdUserDAO cdUserDAO;*/


    @Override
    public void allotPermission(CdURolePermission rolePermission) {
        CdURolePermission entity = new CdURolePermission();
        entity.setRid(rolePermission.getRid());
        //先删除之前的权限分配
        cdURolePermissionDAO.deleteByCondition(entity);

        //添加权限
        String resourcesId = rolePermission.getResourcesId();
        if(!StringUtils.isEmpty(resourcesId)){
            String[] resourcesArr = resourcesId.split(",");
            for(String permissionId : resourcesArr ){
                entity = new CdURolePermission();
                entity.setRid(rolePermission.getRid());
                entity.setPid(Long.parseLong(permissionId));
                cdURolePermissionDAO.insert(entity);
            }
        }

        //更新用户的权限缓存
    }

    @Override
    public List<CdURole> queryRoleListWithSelected(Integer uid) {
        return targetDAO.queryRoleListWithSelected(uid);
    }

    @Override
    public void addUserRole(CdUserRole userRole) {
        CdUserRole entity = new CdUserRole();
        entity.setUid(userRole.getUid());
        cdUserRoleDAO.deleteByCondition(entity);

        String[] roleIds = userRole.getRoleId().split(",");
        for (String roleId : roleIds) {
            entity = new CdUserRole();
            entity.setUid(userRole.getUid());
            entity.setRid(Long.parseLong(roleId));
            cdUserRoleDAO.insert(entity);
        }
        //更新当前登录的用户的权限缓存
        /*List<Integer> userid = new ArrayList<Integer>();
        userid.add(userRole.getUserid());
        myShiroRealm.clearUserAuthByUserId(userid);*/
    }

    @Override
    public Set<String> findRoleByUserId(Long userId) {
        return targetDAO.findRoleByUserId(userId);
    }
}
