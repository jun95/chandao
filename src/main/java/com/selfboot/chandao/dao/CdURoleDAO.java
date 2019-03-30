package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdURole;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface CdURoleDAO extends CrudDAO<CdURole> {

    List<CdURole> queryRoleListWithSelected(@Param("userId") Integer uid);

    Set<String> findRoleByUserId(@Param("userId") Long userId);
}