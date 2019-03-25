package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdRequirementDAO;
import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdRequirementService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hwj on 2019/3/22.
 */
@Service("cdRequirementService")
public class CdRequirementServiceImpl extends BootStrapServiceImpl<CdRequirement, CdRequirementDAO> implements CdRequirementService {


    @Override
    public void updateByProjectId(CdRequirement cdRequirement) {
        targetDAO.updateByProjectId(cdRequirement);
    }

    @Override
    public List<CdRequirement> selectListByProject(CdRequirement entity, Long id) {
        return targetDAO.selectListByProject(entity,id);
    }
}
