package com.selfboot.chandao.service;

import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;


/**
 * Created by 87570 on 2019/3/18.
 */
public interface CdUserService extends BootStrapService<CdUser> {

    void updateByPrimaryKeySelective(CdUser user);

    ServiceResult<CdUser> login(String username, String password);

    List<CdUser> getListByGroupId(CdUser user);

    List<CdUser> getListByConditionWithRole(CdUser user);

    //Map<String, Object> selectUserRecord(CdUser user,int offset, int limit);

}
