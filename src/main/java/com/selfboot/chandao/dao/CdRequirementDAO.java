package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.persist.CrudDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hwj on 2019/3/22.
 */
@Mapper
public interface CdRequirementDAO extends CrudDAO<CdRequirement> {

    void updateByProjectId(CdRequirement cdRequirement);

    List<CdRequirement> selectListByProject(@Param("entity") CdRequirement entity, @Param("userId") Long userId);
}
