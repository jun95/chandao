package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hwj on 2019/3/22.
 */
@Mapper
public interface CdTaskDAO extends CrudDAO<CdTask> {

    List<CdTask> selectListByProject(@Param("entity") CdTask entity, @Param("userId") Long userId);
}
