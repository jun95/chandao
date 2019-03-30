package com.selfboot.chandao.core.realm;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.core.token.ShiroToken;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdUserService;
import com.selfboot.chandao.service.PermissionService;
import com.selfboot.chandao.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * Created by 87570 on 2019/3/17.
 */
public class SimpleRealm extends AuthorizingRealm {

    @Resource
    private CdUserService cdUserService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public SimpleRealm() {
        super();
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        CdUser user= (CdUser) SecurityUtils.getSubject().getPrincipal();

        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //根据用户ID查询角色（role），放入到Authorization里。
        Set<String> roles = roleService.findRoleByUserId(user.getId());
        info.setRoles(roles);

        logger.info("授权的角色信息为：" + JSON.toJSONString(roles));

        Set<String> permissionSet = permissionService.findPermissionByUserId(user.getId());
        info.setStringPermissions(permissionSet);

        logger.info("授权的权限信息为：" + JSON.toJSONString(permissionSet));
        return info;
    }

    /**
     *  认证信息，主要针对用户登录，
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        ShiroToken token = (ShiroToken) authcToken;

        long start = System.currentTimeMillis();
        ServiceResult<CdUser> result = cdUserService.login(token.getUsername(),token.getPwd());

        long end = System.currentTimeMillis();

        logger.info("登录耗时：" + (end - start) + "ms");
        CdUser user = result.getResult();
        if(null == user){
            throw new AccountException("帐号或密码不正确！");
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
        }else if(CdUser.DEL == user.getDeleted().intValue()){
            throw new DisabledAccountException("帐号已经禁止登录！");
        }else{
            //更新登录时间 last login time
            user.setLastLoginTime(new Date());
            cdUserService.updateByPrimaryKeySelective(user);
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(), getName());
    }


}
