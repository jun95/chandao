package com.selfboot.chandao.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie操作工具类
 * Created by hwj on 2019/3/14.
 */
public class CookieManager {

    private CookieManager() {
    }

    /**
     * 获取cookie中的值
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param cookieName
     * @param value
     * @param seconds    cookie有效期(单位为秒)
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String value, int seconds) {
        Cookie cookie = new Cookie(cookieName, value);
        //设置有效期
        cookie.setMaxAge(seconds);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void delCookie(HttpServletRequest request,HttpServletResponse response,String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)){
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");
                System.out.println("被删除的cookie名字为:"+cookie.getName());
                response.addCookie(cookie);
                break;
            }
        }
    }
}
