package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.vo.ProjectProgressVO;

/**
 * Created by 87570 on 2019/3/28.
 */
public interface StatisticsService {

    ProjectProgressVO selectProjectAnalysisResult(CdProject project);
}
