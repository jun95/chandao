package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hwj on 2019/3/21.
 */
@Mapper
public interface CdProjectDAO extends CrudDAO<CdProject> {

    List<CdProject> selectUnCloseProject(@Param("entity") CdProject cdProject, @Param("userId") Long userId);

    List<CdProject> selectListByGroup(@Param("entity") CdProject cdProject,@Param("userId") Long userId);
}
