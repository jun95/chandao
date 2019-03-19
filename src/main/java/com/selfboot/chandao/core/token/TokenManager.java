package com.selfboot.chandao.core.token;

import com.selfboot.chandao.common.Constant;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.util.CookieManager;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 87570 on 2019/3/18.
 */
public class TokenManager {

    private TokenManager() {}

    /**
     * 获取当前登录的用户User对象
     * @return
     */
    public static CdUser getToken(){
        CdUser token = (CdUser) SecurityUtils.getSubject().getPrincipal();
        return token;
    }

    /**
     * 登录
     * @param user
     * @param rememberMe
     * @return
     */
    public static CdUser login(CdUser user,Boolean rememberMe){
        ShiroToken token = new ShiroToken(user.getAccount(), user.getPassword());
        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);
        return getToken();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static void logout(HttpServletResponse response, HttpServletRequest request) {
        logout();
        CookieManager.delCookie(request,response, Constant.USER_TOKEN);
    }
}
