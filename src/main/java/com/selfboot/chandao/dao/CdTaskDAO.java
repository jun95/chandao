package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hwj on 2019/3/22.
 */
@Mapper
public interface CdTaskDAO extends CrudDAO<CdTask> {

}
