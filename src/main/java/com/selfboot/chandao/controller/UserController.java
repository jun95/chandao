package com.selfboot.chandao.controller;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.common.Constant;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.domain.CdUserRole;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdUserService;
import com.selfboot.chandao.service.RoleService;
import com.selfboot.chandao.util.MD5Util;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * Created by 87570 on 2019/3/18.
 */
@RestController
@RequestMapping("user")
@Validated
public class UserController extends BaseController<CdUser, CdUserService> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("getUserInfo")
    public ResponseResult<CdUser> getUserName(HttpServletRequest request) {
        ResponseResult<CdUser> result = new ResponseResult<>(ResponseStatus.OK);
        CdUser user = UserUtil.getUser(request);
        /*UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setAccount(user.getAccount());*/

        user.setPassword(null);
        result.setResult(user);
        return result;
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("getUserRecords")
    public Map<String,Object> getUserRecords(HttpServletRequest request,CdUser cdUser,
                                             @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        //cdUser.setDeleted(1);
        if (!StringUtils.isBlank(id)) {
            cdUser.setId(Long.parseLong(id));
        }
        CdUser user = UserUtil.getUser(request);
        if (user.getType() != null && user.getType() != 1) { //是普通用户
            cdUser.setType(2);
        }

        if (cdUser.getGroupId() == null) {
            return getRecords(cdUser, offset, limit, new DataCallback<CdUser>() {
                @Override
                public List<CdUser> onPushData(CrudService crudService, DataCallbackParam<CdUser> params) {
                    CdUserService cdUserService = (CdUserService) crudService;
                    return cdUserService.getListByConditionWithRole(params.getEntity());
                }
            });
        } else {
            return getRecords(cdUser, offset, limit, new DataCallback<CdUser>() {
                @Override
                public List<CdUser> onPushData(CrudService crudService, DataCallbackParam<CdUser> params) {
                    CdUserService cdUserService = (CdUserService) crudService;
                    return cdUserService.getListByGroupId(params.getEntity());
                }
            });
        }
    }

    /**
     * 保存修改后的用户信息
     * @param cdUser
     * @return
     */
    @PostMapping("saveInfo")
    public ResponseResult<String> saveInfo(HttpServletRequest request,HttpServletResponse response,
                                           @RequestBody @Valid CdUser cdUser) {
        ResponseResult<String> result = new ResponseResult<>();

        Long count = targetService.queryCount(cdUser);
        if (count == 0) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("当前用户不存在");
            return result;
        }

        cdUser.setUpdateId(cdUser.getId());
        cdUser.setUpdateTime(new Date());
        cdUser.setUpdate(true);
        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdUser));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);

            HttpSession session = request.getSession();
            session.setAttribute(Constant.USER_INFO, JSON.toJSONString(cdUser));
            UserUtil.setUser(request,response);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 保存修改后的用户信息
     * @param cdUser
     * @return
     */
    @PostMapping("modifyPwd")
    public ResponseResult<String> modifyPwd(HttpServletRequest request,HttpServletResponse response,
                                           @RequestBody CdUser cdUser) {
        ResponseResult<String> result = new ResponseResult<>();

        CdUser user = UserUtil.getUser(request);
        if (user == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("当前用户不存在/登录过期");
            return result;
        }

        if (StringUtils.isBlank(cdUser.getNewPassword())) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("新密码不能为空");
            return result;
        }

        String password = cdUser.getPassword();
        if (StringUtils.isBlank(cdUser.getPassword())) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("旧密码不能为空");
            return result;
        }

        ServiceResult<CdUser> userServiceResult = targetService.login(user.getAccount(), MD5Util.inputPassFormPass(password));
        if(null == userServiceResult.getResult()){
            throw new AccountException("帐号或密码不正确！");
            /**
             * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
             */
        }else if(CdUser.DEL == userServiceResult.getResult().getDeleted().intValue()){
            throw new DisabledAccountException("帐号已经禁止登录！");
        }

        cdUser.setId(user.getId());
        cdUser.setPassword(MD5Util.inputPassFormPass(cdUser.getNewPassword()));
        cdUser.setUpdateId(cdUser.getId());
        cdUser.setUpdateTime(new Date());
        cdUser.setUpdate(true);
        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdUser));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);

            HttpSession session = request.getSession();
            session.setAttribute(Constant.USER_INFO, JSON.toJSONString(cdUser));
            UserUtil.setUser(request,response);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 添加用户
     * @param cdUser
     * @return
     */
    @RequiresRoles(value={"管理员"},logical = Logical.OR)
    @PostMapping("addMember")
    public ResponseResult<String> addMember(@RequestBody @Valid CdUser cdUser) {
        ResponseResult<String> result = new ResponseResult<>();

        Long count = targetService.queryCount(cdUser);
        if (count > 0) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("当前用户名已经被使用，请更换");
            return result;
        }

        cdUser.setPassword("b7797cce01b4b131b433b6acf4add449");
        cdUser.setDeleted(1);
        cdUser.setType(2); //默认是普通用户
        cdUser.setCreateTime(new Date());
        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdUser));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 禁用用户
     * @param cdUsers
     * @return
     */
    @RequiresRoles(value={"管理员"},logical = Logical.OR)
    @PostMapping("removeMember")
    public ResponseResult<String> removeMember(@RequestBody @NotEmpty(message = "请选择要禁用的记录") List<CdUser> cdUsers) {
        ResponseResult<String> result = new ResponseResult<>();
        if (CollectionUtils.isEmpty(cdUsers)) {
            result.setResponseStatus(ResponseStatus.ILLEGAL_ARG);
            result.setMessage("请选择一条记录");
            return result;
        }
        List<CdUser> cdUserList = new ArrayList<>();
        for (CdUser cdUser : cdUsers) {
            /*cdUser.setId(id);
            cdUser.setDeleted(CdUser.DEL);*/
            cdUser.setUpdate(true);
            cdUserList.add(cdUser);
        }

        ServiceResult serviceResult = targetService.save(cdUserList);
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 保存用户角色
     * @param userRole 用户角色
     *  	  此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
    @RequiresRoles(value={"管理员"},logical = Logical.OR)
    @RequestMapping("/saveUserRoles")
    public ResponseResult<String> saveUserRoles(CdUserRole userRole){
        ResponseResult<String> result = new ResponseResult<>();
        if(org.springframework.util.StringUtils.isEmpty(userRole.getUid())) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("请选择一个用户");
            return result;
        }

        try {
            roleService.addUserRole(userRole);
            result.setResponseStatus(ResponseStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResponseStatus(ResponseStatus.ERROR);
        }
        return result;
    }
}
