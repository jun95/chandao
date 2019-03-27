package com.selfboot.chandao.controller;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.common.Constant;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.core.token.TokenManager;
import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdUserService;
import com.selfboot.chandao.service.PermissionService;
import com.selfboot.chandao.util.CookieManager;
import com.selfboot.chandao.util.MD5Util;
import com.selfboot.chandao.vo.LoginVO;
import org.apache.shiro.authc.DisabledAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by hwj on 2019/3/15.
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CdUserService cdUserService;

    @Resource
    private PermissionService permissionService;


    @PostMapping(value = "login")
    @ResponseBody
    public ResponseResult<List<CdUPermission>> login(HttpServletResponse response,
                                                     HttpServletRequest request,
                                                     @RequestBody @Valid LoginVO loginVO) {
        ResponseResult<List<CdUPermission>> result = new ResponseResult<>();
        logger.info("登录信息为：{}", JSON.toJSONString(loginVO));

        try {
            CdUser entity = new CdUser();
            entity.setAccount(loginVO.getAccount());
            entity.setPassword(MD5Util.inputPassFormPass(loginVO.getPassword()));
            entity = TokenManager.login(entity,false);

            result.setResponseStatus(ResponseStatus.OK);
            result.setMessage("登录成功");

            //设置cookie及用户信息
            HttpSession session = request.getSession();
            session.setAttribute(Constant.USER_INFO,JSON.toJSONString(entity));
            CookieManager.addCookie(response, Constant.USER_TOKEN,session.getId(),30*60);

            //设置菜单权限
            result.setResult(permissionService.loadMenu(entity.getId()));

        } catch (DisabledAccountException e) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("帐号已经禁用");
        } catch (Exception e) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("帐号或密码错误");
        }

        logger.info("响应信息为：{}", JSON.toJSONString(result));
        return result;
    }

    @GetMapping(value = "logout")
    @ResponseBody
    public ResponseResult<String> logout(HttpServletRequest request,HttpServletResponse response) {
        ResponseResult<String> result = new ResponseResult<>();

        try {
            TokenManager.logout(response,request);
            result.setResponseStatus(ResponseStatus.OK);
        } catch (Exception e) {
            result.setResponseStatus(ResponseStatus.ERROR);
        }

        return result;
    }

    @PostMapping(value = "register")
    @ResponseBody
    public ResponseResult<String> register() {
        ResponseResult<String> result = new ResponseResult<>();

        return result;
    }


    @RequestMapping(value = "unauth",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> unauth() {
        ResponseResult<String> result = new ResponseResult<>();
        result.setResult("请先登录");
        return result;
    }
}
