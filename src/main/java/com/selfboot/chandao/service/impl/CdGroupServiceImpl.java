package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.CdGroupDAO;
import com.selfboot.chandao.domain.CdGroup;
import com.selfboot.chandao.persist.BootStrapServiceImpl;
import com.selfboot.chandao.service.CdGroupService;
import org.springframework.stereotype.Service;

/**
 * Created by 87570 on 2019/3/18.
 */
@Service("cdGroupService")
public class CdGroupServiceImpl extends BootStrapServiceImpl<CdGroup, CdGroupDAO> implements CdGroupService {


}
