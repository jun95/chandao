package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 87570 on 2019/3/23.
 */
@Mapper
public interface CdActionLogDAO extends CrudDAO<CdActionLog> {

    CdActionLog selectLatestRecord(@Param("entity") CdActionLog entity);
}
