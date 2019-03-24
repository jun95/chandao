package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.*;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdActionLogService;
import com.selfboot.chandao.service.CdTestTaskService;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/23.
 */
@RestController
@RequestMapping("testtask")
public class CdTestTaskController extends BaseController<CdTestTask,CdTestTaskService> {

    @Autowired
    private CdActionLogService cdActionLogService;

    @GetMapping("getTestTaskRecords")
    public Map<String,Object> getTestTaskRecords(CdTestTask cdTestTask, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdTestTask.setId(Long.parseLong(id));
        }
        cdTestTask.setDeleted(1);
        return getRecords(cdTestTask, offset, limit);
    }

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
}
