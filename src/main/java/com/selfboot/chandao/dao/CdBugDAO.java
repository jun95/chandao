package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 87570 on 2019/3/23.
 */
@Mapper
public interface CdBugDAO extends CrudDAO<CdBug> {

    List<CdBug> selectListByProject(@Param("entity") CdBug entity, @Param("userId") Long userId);
}
