package com.selfboot.chandao.access;

import com.selfboot.chandao.config.constant.FilterURL;
import com.selfboot.chandao.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by hwj on 2019/3/14.
 */
//@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private FilterURL filterURL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 获取调用 获取主要方法
         */
        if (handler instanceof HandlerMethod) {
            logger.info("访问的方法名为:{}",handler);
            //HandlerMethod hm = (HandlerMethod)handler;
            String requestURI = request.getRequestURI();

            if (requestURI.equals("/error")) {
                response.sendRedirect("/login/tologin");
                return false;
            }

            if (!checkIsAccess(requestURI)) {
                HttpUtil.writeJson(response,"请先登录");
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否应该放行
     * @param requestURI
     * @return
     */
    private boolean checkIsAccess(String requestURI) {
        Set<String> ignoredTokenPath = filterURL.getIgnoredTokenPath();
        if (CollectionUtils.isEmpty(ignoredTokenPath)) {
            return false;
        }
        requestURI = requestURI.replaceAll("//","/");
        requestURI = removeJessionIdFromUrl(requestURI);
        if (requestURI.contains("?")) {
            requestURI = requestURI.substring(0,requestURI.indexOf("?"));
        }

        if (!ignoredTokenPath.contains(requestURI)) {
            return false;
        }
        return true;
    }

    private String removeJessionIdFromUrl(String target){
        if (target.contains("JSESSIONID")) {
            target = target.substring(0, target.indexOf(';'));
        }
        return target;
    }
}
