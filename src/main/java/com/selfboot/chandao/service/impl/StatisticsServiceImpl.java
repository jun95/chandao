package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.StatisticsDAO;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.service.StatisticsService;
import com.selfboot.chandao.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 87570 on 2019/3/28.
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsDAO statisticsDAO;

    @Override
    public ProjectProgressVO selectProjectAnalysisResult(CdProject project) {
        return statisticsDAO.selectProjectAnalysisResult(project);
    }

    @Override
    public List<UserProgressVO> selectUserAnalysisList(UserProgressQueryVO userProgressQueryVO) {
        return statisticsDAO.selectUserAnalysisList(userProgressQueryVO);
    }

    @Override
    public List<TestProgressVO> selectTestAnalysisList(TestProgressQueryVO testProgressQueryVO) {
        return statisticsDAO.selectTestAnalysisList(testProgressQueryVO);
    }

    @Override
    public BugProgressVO selectBugAnalysisResult(CdProject project) {
        return statisticsDAO.selectBugAnalysisResult(project);
    }
}
