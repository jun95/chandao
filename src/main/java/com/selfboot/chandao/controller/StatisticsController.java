package com.selfboot.chandao.controller;

import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdProjectService;
import com.selfboot.chandao.service.CdTestTaskService;
import com.selfboot.chandao.service.StatisticsService;
import com.selfboot.chandao.util.UserUtil;
import com.selfboot.chandao.vo.BugProgressVO;
import com.selfboot.chandao.vo.ProjectProgressVO;
import com.selfboot.chandao.vo.TestProgressVO;
import com.selfboot.chandao.vo.UserProgressVO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
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
    private CdTestTaskService cdTestTaskService;

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
        List<UserProgressVO> userProgressVOList = new ArrayList<>();

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

    /**
     * 测试分析
     * @return
     */
    @GetMapping("getTestAnalysisList")
    public Map<String, Object> getTestAnalysisList(CdProject cdProject, @RequestParam(value = "id", required = false) String id,
                                                   @RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "limit", required = false) Integer limit,
                                                   ServletRequest request) {
        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<TestProgressVO> testProgressVOList = new ArrayList<>();

        Map<String, Object> queryResult = cdProjectService.selectRecord(cdProject, offset, limit, new DataCallback<CdProject>() {
            @Override
            public List<CdProject> onPushData(CrudService crudService, DataCallbackParam<CdProject> params) {
                return cdProjectService.selectListByGroup(params.getEntity(), UserUtil.getUser(request).getId());
            }
        });
        List<CdProject> list = (List<CdProject>) queryResult.get("data");

        if (!CollectionUtils.isEmpty(list)) {
            total = (long) queryResult.get("total");
            testProgressVOList = statisticsService.selectTestAnalysisList(list);

            CdTestTask entity = null;
            for (TestProgressVO testProgressVO : testProgressVOList) {
                entity = new CdTestTask();
                entity.setProjectId(testProgressVO.getProjectId());
                testProgressVO.setTotalTestTaskNum(Float.valueOf(cdTestTaskService.queryCount(entity)));
            }
        }

        responseContent.put("rows", testProgressVOList);
        responseContent.put("total",total);

        return responseContent;
    }

    /**
     * BUG分析
     * @return
     */
    @GetMapping("getBugAnalysisList")
    public Map<String, Object> getBugAnalysisList(CdProject cdProject, @RequestParam(value = "id", required = false) String id,
                                                   @RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "limit", required = false) Integer limit,
                                                   ServletRequest request) {
        Map<String,Object> responseContent = new HashMap<>();
        long total = 0;
        List<BugProgressVO> bugProgressVOList = new ArrayList<>();

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
                BugProgressVO bugProgressVO = statisticsService.selectBugAnalysisResult(project);
                bugProgressVO.setProjectId(project.getId());
                bugProgressVO.setProjectName(project.getName());
                bugProgressVOList.add(bugProgressVO);
            }
            //bugProgressVOList = statisticsService.selectBugAnalysisResult(list);
        }

        responseContent.put("rows", bugProgressVOList);
        responseContent.put("total",total);

        return responseContent;
    }
}
