package com.selfboot.chandao.vo;

import java.io.Serializable;

/**
 * Created by 87570 on 2019/3/18.
 */
public class UserInfoVO implements Serializable {

    private Long id;

    private String account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
