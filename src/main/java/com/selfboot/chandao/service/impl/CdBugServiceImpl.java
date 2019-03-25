package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdBugDAO;
import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdBugService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87570 on 2019/3/23.
 */
@Service("cdBugService")
public class CdBugServiceImpl extends BootStrapServiceImpl<CdBug,CdBugDAO> implements CdBugService {


    @Override
    public List<CdBug> selectListByProject(CdBug entity, Long id) {
        return targetDAO.selectListByProject(entity,id);
    }
}
