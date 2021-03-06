package com.selfboot.chandao.core.filter;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.config.constant.FilterURL;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.util.HttpUtil;
import com.selfboot.chandao.util.UserUtil;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

/**
 * AccessControlFilter
 * Created by 87570 on 2019/3/18.
 */
public class LoginFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FilterURL filterURL;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        logger.info("拦截URL：" + requestURI);

        CdUser token = UserUtil.getUser(request);

        if(null != token || isLoginRequest(request, response)){// && isEnabled()
            return Boolean.TRUE;
        }

        if (HttpUtil.isAjax(request)) {

            if (!checkIsAccess(requestURI)) {
                ResponseResult<String> result = new ResponseResult<>(ResponseStatus.ERROR);
                result.setMessage("当前用户没有登录");
                //HttpUtil.writeJson(response,"当前用户没有登录!");
                try {
                    HttpUtil.writeJson(response, JSON.toJSONString(result));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
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

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //保存Request和Response 到登录后的链接
        //saveRequestAndRedirectToLogin(servletRequest, servletResponse);
        return Boolean.FALSE ;
    }
}
