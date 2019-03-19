package com.selfboot.chandao.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hwj on 2019/3/15.
 */
public class HttpUtil {

    private HttpUtil() {}

    public static void writeJson(ServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        out.write(msg.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    /**
     * 是否是Ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }
}
