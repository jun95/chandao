package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ProjectStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdActionLogService;
import com.selfboot.chandao.service.CdTestTaskService;
import com.selfboot.chandao.util.ActionLogHelper;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/23.
 */
@RestController
@RequestMapping("testtask")
public class CdTestTaskController extends BaseController<CdTestTask,CdTestTaskService> {

    @Autowired
    private CdActionLogService cdActionLogService;

    /**
     * 获取测试任务列表
     * @return
     */
    @GetMapping("getTestTaskRecords")
    public Map<String,Object> getTestTaskRecords(HttpServletRequest request,CdTestTask cdTestTask,
                                                 @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdTestTask.setId(Long.parseLong(id));
        }
        cdTestTask.setDeleted(1);
        if (UserUtil.isAdmin(request)) {
            return getRecords(cdTestTask, offset, limit);
        }

        return getRecords(cdTestTask, offset, limit, new DataCallback<CdTestTask>() {
            @Override
            public List<CdTestTask> onPushData(CrudService crudService, DataCallbackParam<CdTestTask> params) {
                return targetService.selectListByProject(params.getEntity(),UserUtil.getUser(request).getId());
            }
        });
    }

    /**
     * 新增测试任务
     * @param request
     * @param cdTestTask
     * @return
     */
    @PostMapping("addTestTask")
    public ResponseResult<String> addTestTask(HttpServletRequest request, @RequestBody @Valid CdTestTask cdTestTask) {
        ResponseResult<String> result = new ResponseResult<>();

        CdUser user = UserUtil.getUser(request);
        /*cdTestTask.setOpenedBy(user.getId());
        cdTestTask.setOpenedName(user.getAccount());
        cdTestTask.setOpenedDate(new Date());*/

        /*cdTestTask.setAssignedDate(new Date());*/
        cdTestTask.setOwner(user.getAccount());
        cdTestTask.setStatus(ProjectStatusEnum.WAIT.getStatusName());
        cdTestTask.setDeleted(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdTestTask));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 删除测试任务
     * @param request
     * @param cdTestTask
     * @return
     */
    @PostMapping("delete")
    public ResponseResult<String> delete(HttpServletRequest request,@RequestBody CdTestTask cdTestTask) {
        ResponseResult<String> result = new ResponseResult<>();

        if (cdTestTask.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要删除的测试任务");
            return result;
        }

        CdTestTask testTask = new CdTestTask();
        testTask.setId(cdTestTask.getId());
        ServiceResult serviceResult = targetService.queryOne(testTask);
        if (serviceResult.isSuccess()) {
            cdTestTask = (CdTestTask) serviceResult.getResult();
            if (cdTestTask != null) {
                cdTestTask.setDeleted(CdTestTask.DEL);
                cdTestTask.setUpdate(true);

                serviceResult = targetService.save(Collections.singletonList(cdTestTask));
                if (serviceResult.isSuccess()) {
                    result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("测试任务不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    /**
     * 展示测试任务详情
     * @param request
     * @param cdTestTask
     * @return
     */
    @PostMapping("showDetail")
    public ResponseResult<CdTestTask> showDetail(HttpServletRequest request,@RequestBody CdTestTask cdTestTask) {
        ResponseResult<CdTestTask> result = new ResponseResult<>();

        if (cdTestTask.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要查看的测试任务");
            return result;
        }

        CdTestTask testTask = new CdTestTask();
        testTask.setId(cdTestTask.getId());
        ServiceResult serviceResult = targetService.queryOne(testTask);
        if (serviceResult.isSuccess()) {
            cdTestTask = (CdTestTask) serviceResult.getResult();
            if (cdTestTask != null) {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                result.setResult(cdTestTask);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("测试任务不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    /**
     * 编辑测试任务
     * @param request
     * @param cdTestTask
     * @return
     */
    @PostMapping("edit")
    public ResponseResult<CdTask> edit(HttpServletRequest request, @RequestBody CdTestTask cdTestTask) {
        ResponseResult<CdTask> result = new ResponseResult<>();

        if (cdTestTask.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要编辑的任务");
            return result;
        }

        CdTestTask testTask = new CdTestTask();
        testTask.setId(cdTestTask.getId());
        ServiceResult serviceResult = targetService.queryOne(testTask);
        if (serviceResult.isSuccess()) {
            testTask = (CdTestTask) serviceResult.getResult();
            if (testTask != null) {
                cdTestTask.setUpdate(true);
                targetService.save(Collections.singletonList(cdTestTask));
                result.setResponseStatus(ResponseStatus.OK);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("项目不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    /**
     * 更新测试任务状态
     * @param request
     * @param cdTestTask
     * @return
     */
    @PostMapping("updateStatus")
    public ResponseResult<String> updateStatus(HttpServletRequest request,@RequestBody CdTestTask cdTestTask) {
        ResponseResult<String> result = new ResponseResult<>();
        CdTestTask query = new CdTestTask();
        query.setId(cdTestTask.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (serviceResult.isSuccess()) {
            query = (CdTestTask) serviceResult.getResult();
            if (query != null) {
                cdTestTask.setUpdate(true);
                CdUser user = UserUtil.getUser(request);

                ProjectStatusEnum status = ProjectStatusEnum.getStatus(cdTestTask.getStatus());
                buildFinalTestTask(cdTestTask,status,user);

                CdActionLog cdActionLog = ActionLogHelper.buildTestTaskLog(user.getAccount(), query,
                        cdTestTask.getStatusDesc(), user.getId());
                cdActionLogService.save(Collections.singletonList(cdActionLog));

                serviceResult = targetService.save(Collections.singletonList(cdTestTask));
                if (serviceResult.isSuccess()) {

                    result.setResponseStatus(ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("测试任务不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    private void buildFinalTestTask(CdTestTask task, ProjectStatusEnum status, CdUser user) {
        switch (status) {
            case DONE:
                task.setFinishedBy(user.getId());
                task.setFinishedName(user.getAccount());
                task.setFinishedDate(new Date());
                break;
            case DOING:
                task.setOpenedBy(user.getId());
                task.setOpenedName(user.getAccount());
                task.setOpenedDate(new Date());
                break;
        }
    }

    /**
     * 指派测试任务
     * @param request
     * @param cdTestTask
     * @return
     */
    @PostMapping("assigned")
    public ResponseResult<String> assigned(HttpServletRequest request, @RequestBody CdTestTask cdTestTask) {
        ResponseResult<String> result = new ResponseResult<>();

        if (cdTestTask.getId() == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("请选择要指派的测试任务");
            return result;
        }

        CdTestTask query = new CdTestTask();
        query.setId(cdTestTask.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (!serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
            return result;
        }

        query = (CdTestTask) serviceResult.getResult();
        if (query == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("测试任务不存在");
            return result;
        }

        //CdUser user = UserUtil.getUser(request);
        cdTestTask.setUpdate(true);
        cdTestTask.setAssignedDate(new Date());

        serviceResult = targetService.save(Collections.singletonList(cdTestTask));
        if (serviceResult.isSuccess()) {

            if (query.getAssignedBy() == null) {
                CdActionLog cdActionLog = new CdActionLog();
                cdActionLog.setObjectType("testtask");
                cdActionLog.setObjectId(query.getId());

                cdActionLog = cdActionLogService.selectLatestRecord(cdActionLog);
                query.setAssignedBy(cdActionLog.getAssignedBy());
            }

            CdActionLog cdActionLog = ActionLogHelper.buildTestTaskLog(cdTestTask.getAssignedByName(), query,
                    "指派", cdTestTask.getAssignedBy());
            cdActionLogService.save(Collections.singletonList(cdActionLog));
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
