package com.selfboot.chandao.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangqj on 2017/4/19.
 */
@Configuration
@ConfigurationProperties(prefix="spring.datasource")
public class DruidConfig {

	private String username;
	private String password;

	@Bean
	public ServletRegistrationBean druidServlet() {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		//登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername",username);
		servletRegistrationBean.addInitParameter("loginPassword",password);
		return servletRegistrationBean;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
