package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 87570 on 2019/3/18.
 */
@Mapper
public interface CdUserDAO extends CrudDAO<CdUser> {

}
