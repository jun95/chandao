package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.persist.BootStrapService;

import java.util.List;

/**
 * Created by hwj on 2019/3/22.
 */
public interface CdRequirementService extends BootStrapService<CdRequirement> {

    void updateByProjectId(CdRequirement cdRequirement);

    List<CdRequirement> selectListByProject(CdRequirement entity, Long id);
}
