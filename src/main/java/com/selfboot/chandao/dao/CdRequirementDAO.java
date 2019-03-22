package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hwj on 2019/3/22.
 */
@Mapper
public interface CdRequirementDAO extends CrudDAO<CdRequirement> {
}
