package com.selfboot.chandao.service.impl;

import com.selfboot.chandao.dao.StatisticsDAO;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.service.StatisticsService;
import com.selfboot.chandao.vo.ProjectProgressVO;
import com.selfboot.chandao.vo.UserProgressVO;
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
    public List<UserProgressVO> selectUserAnalysisList(List<CdProject> list) {
        return statisticsDAO.selectUserAnalysisList(list);
    }
}
