package com.selfboot.chandao.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdProjectService;
import com.selfboot.chandao.service.CdTestTaskService;
import com.selfboot.chandao.service.StatisticsService;
import com.selfboot.chandao.util.UserUtil;
import com.selfboot.chandao.vo.*;
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

        Map<String, Object> queryResult = getProjectByCurrUser(request,cdProject,offset,limit);
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
     * 通过当前用户可见的项目信息
     * @param request
     * @param cdProject
     * @param offset
     * @param limit
     * @return
     */
    private Map<String, Object> getProjectByCurrUser(ServletRequest request,CdProject cdProject,Integer offset,Integer limit) {
        Map<String, Object> queryResult = null;
        cdProject.setDeleted(1);
        if (UserUtil.isAdmin(request)) {
            queryResult = cdProjectService.selectRecord(cdProject, offset, limit);
        } else {
            queryResult = cdProjectService.selectRecord(cdProject, offset, limit, new DataCallback<CdProject>() {
                @Override
                public List<CdProject> onPushData(CrudService crudService, DataCallbackParam<CdProject> params) {
                    return cdProjectService.selectListByGroup(params.getEntity(), UserUtil.getUser(request).getId());
                }
            });
        }
        return queryResult;
    }

    /**
     * 人员分析
     * @return
     */
    @GetMapping("getUserAnalysisList")
    public Map<String, Object> getUserAnalysisList(UserProgressQueryVO queryVO,
                                                   @RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "limit", required = false) Integer limit,
                                                   ServletRequest request) {
        if (!UserUtil.isAdmin(request)) {
            queryVO.setProjectCreateBy(UserUtil.getUser(request).getId());
        }
        return selectUserAnalysisList(queryVO, offset, limit);
    }

    private Map<String, Object> selectUserAnalysisList(UserProgressQueryVO queryVO, Integer offset, Integer limit) {
        // 准备结果集
        Map<String, Object> resultSet = new HashMap<>();
        long total = 0;
        boolean isPagination = true;

        offset = (offset == null ? 0 : offset);
        limit = (limit == null ? 10 : limit);

        // 检查是否需要分页
        if (offset < 0 && limit < 0)
            isPagination = false;

        if (isPagination) {
            PageHelper.offsetPage(offset, limit);
        }

        List<UserProgressVO> recordDOS = statisticsService.selectUserAnalysisList(queryVO);;

        total = isPagination ? new PageInfo<>(recordDOS).getTotal() :
                CollectionUtils.isEmpty(recordDOS) ? 0 : recordDOS.size();

        resultSet.put("rows", recordDOS);
        resultSet.put("total", total);
        return resultSet;
    }

    /**
     * 测试分析
     * @return
     */
    @GetMapping("getTestAnalysisList")
    public Map<String, Object> getTestAnalysisList(TestProgressQueryVO queryVO,
                                                   @RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "limit", required = false) Integer limit,
                                                   ServletRequest request) {

        if (!UserUtil.isAdmin(request)) {
            queryVO.setProjectCreateBy(UserUtil.getUser(request).getId());
        }
        Map<String, Object> queryResult = selectTestAnalysisList(queryVO, offset, limit);

        return queryResult;
    }

    private Map<String, Object> selectTestAnalysisList(TestProgressQueryVO queryVO, Integer offset, Integer limit) {
        // 准备结果集
        Map<String, Object> resultSet = new HashMap<>();
        long total = 0;
        boolean isPagination = true;

        offset = (offset == null ? 0 : offset);
        limit = (limit == null ? 10 : limit);

        // 检查是否需要分页
        if (offset < 0 && limit < 0)
            isPagination = false;

        if (isPagination) {
            PageHelper.offsetPage(offset, limit);
        }

        List<TestProgressVO> recordDOS = statisticsService.selectTestAnalysisList(queryVO);

        if (!CollectionUtils.isEmpty(recordDOS)) {
            CdTestTask entity = null;
            for (TestProgressVO testProgressVO : recordDOS) {
                entity = new CdTestTask();
                entity.setProjectId(testProgressVO.getProjectId());
                testProgressVO.setTotalTestTaskNum(Float.valueOf(cdTestTaskService.queryCount(entity)));
            }
        }

        total = isPagination ? new PageInfo<>(recordDOS).getTotal() :
                CollectionUtils.isEmpty(recordDOS) ? 0 : recordDOS.size();

        resultSet.put("rows", recordDOS);
        resultSet.put("total", total);
        return resultSet;
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

        Map<String, Object> queryResult = getProjectByCurrUser(request,cdProject,offset,limit);
        List<CdProject> list = (List<CdProject>) queryResult.get("data");

        if (!CollectionUtils.isEmpty(list)) {
            total = (long) queryResult.get("total");
            for (CdProject project : list) {
                BugProgressVO bugProgressVO = statisticsService.selectBugAnalysisResult(project);
                bugProgressVO.setProjectId(project.getId());
                bugProgressVO.setProjectName(project.getName());
                bugProgressVOList.add(bugProgressVO);
            }
        }

        responseContent.put("rows", bugProgressVOList);
        responseContent.put("total",total);

        return responseContent;
    }
}
