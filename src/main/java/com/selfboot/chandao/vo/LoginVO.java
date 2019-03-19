package com.selfboot.chandao.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by hwj on 2019/3/15.
 */
public class LoginVO implements Serializable {
    @NotNull
    private String account;
    @NotNull
    private String password;

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
}
