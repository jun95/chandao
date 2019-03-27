package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CdUPermissionDAO extends CrudDAO<CdUPermission> {

    Integer selectSort(@Param("entity") CdUPermission cdUPermission);

    void deletePermission(@Param("id") Long id);

    List<CdUPermission> queryResourcesListWithSelected(@Param("rid") Integer rid);

    List<CdUPermission> getMenu(@Param("userId") Long userId,@Param("type") Integer type);
}