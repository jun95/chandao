package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 87570 on 2019/3/18.
 */
@Mapper
public interface CdUserDAO extends CrudDAO<CdUser> {

    List<CdUser> getListByGroupId(@Param("entity") CdUser user);
}
