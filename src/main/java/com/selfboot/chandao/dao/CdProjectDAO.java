package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hwj on 2019/3/21.
 */
@Mapper
public interface CdProjectDAO extends CrudDAO<CdProject> {

}
