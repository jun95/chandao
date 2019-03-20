package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdUserGroup;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hwj on 2019/3/20.
 */
@Mapper
public interface CdUserGroupDAO extends CrudDAO<CdUserGroup> {

}
