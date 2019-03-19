package com.selfboot.chandao.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 需要被过滤的url
 * Created by hwj on 2019/3/15.
 */
@Component
@ConfigurationProperties(prefix="filter")
public class FilterURL {
    /**
     * 被拦截器过滤忽略的URL
     */
    private Set<String> ignoredTokenPath;

    public Set<String> getIgnoredTokenPath() {
        return ignoredTokenPath;
    }

    public void setIgnoredTokenPath(Set<String> ignoredTokenPath) {
        this.ignoredTokenPath = ignoredTokenPath;
    }
}
