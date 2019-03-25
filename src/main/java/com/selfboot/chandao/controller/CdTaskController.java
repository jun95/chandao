package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.*;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdActionLogService;
import com.selfboot.chandao.service.CdTaskService;
import com.selfboot.chandao.util.ActionLogHelper;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by hwj on 2019/3/22.
 */
@RestController
@RequestMapping("task")
public class CdTaskController extends BaseController<CdTask, CdTaskService> {

    @Autowired
    private CdActionLogService cdActionLogService;

    @GetMapping("getTaskRecords")
    public Map<String,Object> getTaskRecords(CdTask cdTask, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdTask.setId(Long.parseLong(id));
        }
        cdTask.setDeleted(1);
        return getRecords(cdTask, offset, limit);
    }


    @PostMapping("addTask")
    public ResponseResult<String> addTask(HttpServletRequest request, @RequestBody @Valid CdTask cdTask) {
        ResponseResult<String> result = new ResponseResult<>();

        /*CdUser user = UserUtil.getUser(request);
        cdTask.setOpenedBy(user.getId());
        cdTask.setOpenedName(user.getAccount());
        cdTask.setOpenedDate(new Date());

        cdTask.setAssignedDate(new Date());*/

        cdTask.setStatus(TaskStatusEnum.WAIT.getStatusName());
        cdTask.setDeleted(1);
        cdTask.setType(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdTask));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("updateStatus")
    public ResponseResult<String> updateStatus(HttpServletRequest request,@RequestBody CdTask cdTask) {
        ResponseResult<String> result = new ResponseResult<>();
        CdTask query = new CdTask();
        query.setId(cdTask.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (serviceResult.isSuccess()) {
            query = (CdTask) serviceResult.getResult();
            if (query != null) {
                cdTask.setUpdate(true);

                CdUser user = UserUtil.getUser(request);
                cdTask.setLastEditedBy(user.getId());
                cdTask.setLastEditedName(user.getAccount());
                cdTask.setLastEditedDate(new Date());

                TaskStatusEnum status = TaskStatusEnum.getStatus(cdTask.getStatus());
                buildFinalTask(cdTask,status,user);

                CdActionLog cdActionLog = ActionLogHelper.buildTaskLog(user.getAccount(), query,
                        cdTask.getStatusDesc(), user.getId());
                cdActionLogService.save(Collections.singletonList(cdActionLog));

                serviceResult = targetService.save(Collections.singletonList(cdTask));
                if (serviceResult.isSuccess()) {
                    result.setResponseStatus(ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("任务不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    private void buildFinalTask(CdTask task, TaskStatusEnum status, CdUser user) {
        switch (status) {
            case DONE:
                task.setFinishedBy(user.getId());
                task.setFinishedName(user.getAccount());
                task.setFinishedDate(new Date());
                break;
            case CANCEL:
                task.setCanceledBy(user.getId());
                task.setCanceledName(user.getAccount());
                task.setCanceledDate(new Date());
                break;
            case CLOSED:
                task.setClosedBy(user.getId());
                task.setClosedName(user.getAccount());
                task.setClosedDate(new Date());
                break;
            case DOING:
                task.setOpenedBy(user.getId());
                task.setOpenedName(user.getAccount());
                task.setOpenedDate(new Date());
                break;
        }
    }

    @PostMapping("assigned")
    public ResponseResult<String> assigned(HttpServletRequest request, @RequestBody CdTask cdTask) {
        ResponseResult<String> result = new ResponseResult<>();

        /*CdUser user = UserUtil.getUser(request);
        cdTask.setOpenedBy(user.getId());
        cdTask.setOpenedName(user.getAccount());
        cdTask.setOpenedDate(new Date());

        cdTask.setAssignedDate(new Date());*/

        //cdTask.setStatus(ProjectStatusEnum.WAIT.getStatusName());
        //cdTask.setDeleted(1);
        if (cdTask.getId() == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("请选择要指派的任务");
            return result;
        }

        CdTask query = new CdTask();
        query.setId(cdTask.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (!serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
            return result;
        }

        query = (CdTask) serviceResult.getResult();
        if (query == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("任务不存在");
            return result;
        }

        CdUser user = UserUtil.getUser(request);
        cdTask.setLastEditedBy(user.getId());
        cdTask.setLastEditedName(user.getAccount());
        cdTask.setLastEditedDate(new Date());
        cdTask.setUpdate(true);
        cdTask.setAssignedDate(new Date());

        serviceResult = targetService.save(Collections.singletonList(cdTask));
        if (serviceResult.isSuccess()) {

            CdActionLog cdActionLog = ActionLogHelper.buildTaskLog(cdTask.getAssignedByName(), query,
                    "指派", cdTask.getAssignedBy());
            cdActionLogService.save(Collections.singletonList(cdActionLog));
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
