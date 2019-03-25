package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 87570 on 2019/3/23.
 */
@Mapper
public interface CdTestTaskDAO extends CrudDAO<CdTestTask> {

    List<CdTestTask> selectListByProject(@Param("entity") CdTestTask entity, @Param("userId") Long userId);
}
