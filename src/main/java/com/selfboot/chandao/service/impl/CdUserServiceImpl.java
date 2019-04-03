package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.dao.CdUserDAO;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87570 on 2019/3/18.
 */
@Service("cdUserService")
public class CdUserServiceImpl extends BootStrapServiceImpl<CdUser,CdUserDAO> implements CdUserService {

    @Override
    public void updateByPrimaryKeySelective(CdUser user) {
        targetDAO.updateByPrimaryKeySelective(user);
    }

    @Override
    public ServiceResult<CdUser> login(String username, String password) {
        CdUser entity = new CdUser();
        entity.setAccount(username);
        entity.setPassword(password);

        return super.queryOne(entity);
    }

    @Override
    public List<CdUser> getListByGroupId(CdUser user) {

        return targetDAO.getListByGroupId(user);
    }

    @Override
    public List<CdUser> getListByConditionWithRole(CdUser user) {
        return targetDAO.selectListByConditionWithRole(user);
    }

    /*@Override
    public Map<String, Object> selectUserRecord(CdUser user, int offset, int limit) {
        // 准备结果集
        Map<String, Object> resultSet = new HashMap<>();
        long total = 0;
        boolean isPagination = true;

        // 检查是否需要分页
        if (offset < 0 && limit < 0)
            isPagination = false;

        if (isPagination) {
            PageHelper.offsetPage(offset, limit);
        }

        List<CdUser> userRecordDOS = (List<CdUser>) super.queryList(user).getResult();

        total = isPagination ? new PageInfo<>(userRecordDOS).getTotal() :
                CollectionUtils.isEmpty(userRecordDOS) ? 0 : userRecordDOS.size();

        resultSet.put("data", userRecordDOS);
        resultSet.put("total", total);
        return resultSet;
    }*/
}
