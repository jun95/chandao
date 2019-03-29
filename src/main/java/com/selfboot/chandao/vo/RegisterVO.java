package com.selfboot.chandao.vo;

import javax.validation.constraints.NotBlank;

/**
 * 注册信息
 * Created by hwj on 2019/3/29.
 */
public class RegisterVO {
    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    private String account;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 重复密码
     */
    @NotBlank(message = "重复密码不能为空")
    private String repeatPassword;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
