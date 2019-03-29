package com.selfboot.chandao.service;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.vo.*;

import java.util.List;

/**
 * Created by 87570 on 2019/3/28.
 */
public interface StatisticsService {

    ProjectProgressVO selectProjectAnalysisResult(CdProject project);

    List<UserProgressVO> selectUserAnalysisList(UserProgressQueryVO userProgressQueryVO);

    List<TestProgressVO> selectTestAnalysisList(TestProgressQueryVO testProgressQueryVO);

    BugProgressVO selectBugAnalysisResult(CdProject project);
}
