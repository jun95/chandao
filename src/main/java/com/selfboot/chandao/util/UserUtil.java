package com.selfboot.chandao.util;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.common.Constant;
import com.selfboot.chandao.core.token.TokenManager;
import com.selfboot.chandao.domain.CdUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 87570 on 2019/3/18.
 */
public class UserUtil {

    private static Logger logger = LoggerFactory.getLogger(UserUtil.class);

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

        //logger.info("从cookie中获取的token信息为：" + JSON.toJSONString(token));
        if (token == null) {
            token = TokenManager.getToken();
            //logger.info("从shiro中获取的token信息为：" + JSON.toJSONString(token));
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
