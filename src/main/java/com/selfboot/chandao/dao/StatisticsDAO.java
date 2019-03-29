package com.selfboot.chandao.dao;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.vo.ProjectProgressVO;
import com.selfboot.chandao.vo.UserProgressVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 87570 on 2019/3/28.
 */
@Mapper
public interface StatisticsDAO {

    ProjectProgressVO selectProjectAnalysisResult(@Param("entity") CdProject project);

    List<UserProgressVO> selectUserAnalysisList(@Param("projectList") List<CdProject> list);
}
