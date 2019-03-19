package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdUserRole;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CdUserRoleDAO extends CrudDAO<CdUserRole> {

}