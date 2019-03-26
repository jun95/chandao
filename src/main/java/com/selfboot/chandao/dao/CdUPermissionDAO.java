package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CdUPermissionDAO extends CrudDAO<CdUPermission> {

    Integer selectSort(@Param("entity") CdUPermission cdUPermission);

    void deletePermission(@Param("id") Long id);
}