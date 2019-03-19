package com.selfboot.chandao.util;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.common.Constant;
import com.selfboot.chandao.core.token.TokenManager;
import com.selfboot.chandao.domain.CdUser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 87570 on 2019/3/18.
 */
public class UserUtil {

    private UserUtil() {}

    public static CdUser getUser(ServletRequest request) {
        CdUser token = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String cookieValue = CookieManager.getCookieValue(httpServletRequest, Constant.USER_TOKEN);
        if (!StringUtils.isBlank(cookieValue)) {
            HttpSession session = SessionContext.getInstance().getSession(cookieValue);
            if (session != null) {
                String userInfo = (String) session.getAttribute(Constant.USER_INFO);
                token = JSON.parseObject(userInfo,CdUser.class);
            }
        }
        if (token == null) {
            token = TokenManager.getToken();
        }
        /*if (token == null) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String cookieValue = CookieManager.getCookieValue(httpServletRequest, Constant.USER_TOKEN);
            if (!StringUtils.isBlank(cookieValue)) {
                HttpSession session = SessionContext.getInstance().getSession(cookieValue);
                if (session != null) {
                    String userInfo = (String) session.getAttribute(Constant.USER_INFO);
                    token = JSON.parseObject(userInfo,CdUser.class);
                }
            }
        }*/
        return token;
    }
}
