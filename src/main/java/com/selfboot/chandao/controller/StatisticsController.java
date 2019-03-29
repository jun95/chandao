package com.selfboot.chandao.controller;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdProjectService;
import com.selfboot.chandao.service.CdUserService;
import com.selfboot.chandao.service.StatisticsService;
import com.selfboot.chandao.util.UserUtil;
import com.selfboot.chandao.vo.ProjectProgressVO;
import com.selfboot.chandao.vo.UserProgressVO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计模块contriller
 * Created by hwj on 2019/3/28.
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private CdProjectService cdProjectService;

    @Resource
    private StatisticsService statisticsService;

    @Resource
    private CdUserService cdUserService;

    /**
     * 项目分析
     * @return
     */
    @GetMapping("getProjectAnalysisList")
    public Map<String, Object> getProjectAnalysisList(CdProject cdProject, @RequestParam(value = "id", required = false) String id,
                                                      @RequestParam(value = "offset", required = false) Integer offset,
                                                      @RequestParam(value = "limit", required = false) Integer limit,
                                                      ServletRequest request) {

        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<ProjectProgressVO> projectProgressVOList = new ArrayList<>(limit);

        Map<String, Object> queryResult = cdProjectService.selectRecord(cdProject, offset, limit, new DataCallback<CdProject>() {
            @Override
            public List<CdProject> onPushData(CrudService crudService, DataCallbackParam<CdProject> params) {
                return cdProjectService.selectListByGroup(params.getEntity(), UserUtil.getUser(request).getId());
            }
        });
        List<CdProject> list = (List<CdProject>) queryResult.get("data");

        if (!CollectionUtils.isEmpty(list)) {
            total = (long) queryResult.get("total");
            for (CdProject project : list) {
                ProjectProgressVO projectProgressVO = statisticsService.selectProjectAnalysisResult(project);
                projectProgressVO.setProjectName(project.getName());
                projectProgressVOList.add(projectProgressVO);
            }
        }

        responseContent.put("rows", projectProgressVOList);
        responseContent.put("total",total);

        return responseContent;
    }

    /**
     * 人员分析
     * @return
     */
    @GetMapping("getUserAnalysisList")
    public Map<String, Object> getUserAnalysisList(CdProject cdProject, @RequestParam(value = "id", required = false) String id,
                                                   @RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "limit", required = false) Integer limit,
                                                   ServletRequest request) {
        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<UserProgressVO> userProgressVOList = null;

        Map<String, Object> queryResult = cdProjectService.selectRecord(cdProject, offset, limit, new DataCallback<CdProject>() {
            @Override
            public List<CdProject> onPushData(CrudService crudService, DataCallbackParam<CdProject> params) {
                return cdProjectService.selectListByGroup(params.getEntity(), UserUtil.getUser(request).getId());
            }
        });
        List<CdProject> list = (List<CdProject>) queryResult.get("data");

        if (!CollectionUtils.isEmpty(list)) {
            total = (long) queryResult.get("total");
            userProgressVOList = statisticsService.selectUserAnalysisList(list);
        }

        responseContent.put("rows", userProgressVOList);
        responseContent.put("total",total);

        return responseContent;
    }


}
