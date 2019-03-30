package com.selfboot.chandao.domain;

import com.selfboot.chandao.persist.BaseEntity;
import com.selfboot.chandao.validator.MobileCheck;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class CdUser extends BaseEntity implements Serializable {

    /** 账号 */
    @NotBlank(message = "账号不能为空")
    private String account;

    /** 密码 */
    private String password;

    /** 1:管理员；2：普通用户 */
    private Integer type;

    /** 真实姓名 */
    private String realname;

    /** 昵称 */
    private String nickname;

    /** 手机号 */
    @MobileCheck
    private String mobile;

    /** 性别,1:男；2：女 */
    private Integer gender;

    /** 最后登录时间 */
    private Date lastLoginTime;

    /** 创建时间 */
    private Date createTime;

    /** 创建时间 */
    private Date updateTime;

    /** 更新人id */
    private Long updateId;

    private String nicknameLike;

    private String accountLike;

    private Long groupId;

    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getNicknameLike() {
        return nicknameLike;
    }

    public void setNicknameLike(String nicknameLike) {
        this.nicknameLike = nicknameLike;
    }

    public String getAccountLike() {
        return accountLike;
    }

    public void setAccountLike(String accountLike) {
        this.accountLike = accountLike;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

}