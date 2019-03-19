package com.selfboot.chandao.core.filter;

import com.selfboot.chandao.config.constant.FilterURL;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.util.HttpUtil;
import com.selfboot.chandao.util.UserUtil;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by 87570 on 2019/3/18.
 */
public class LoginFilter extends AccessControlFilter {

    @Resource
    private FilterURL filterURL;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {

        CdUser token = UserUtil.getUser(request);

        if(null != token || isLoginRequest(request, response)){// && isEnabled()
            return Boolean.TRUE;
        }

        if (HttpUtil.isAjax(request)) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String requestURI = httpServletRequest.getRequestURI();

            if (!checkIsAccess(requestURI)) {
                HttpUtil.writeJson(response,"当前用户没有登录!");
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
