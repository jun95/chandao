package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.*;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdActionLogService;
import com.selfboot.chandao.service.CdBugService;
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
@RequestMapping("bug")
public class CdBugController extends BaseController<CdBug,CdBugService> {

    @Autowired
    private CdActionLogService cdActionLogService;

    @GetMapping("getBugRecords")
    public Map<String,Object> getBugRecords(HttpServletRequest request,CdBug cdBug,
                                            @RequestParam(value = "id",required = false) String id,
                                                 @RequestParam(value = "offset",required = false) Integer offset,
                                                 @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdBug.setId(Long.parseLong(id));
        }
        cdBug.setDeleted(1);
        return getRecords(cdBug, offset, limit, new DataCallback<CdBug>() {
            @Override
            public List<CdBug> onPushData(CrudService crudService, DataCallbackParam<CdBug> params) {
                return targetService.selectListByProject(params.getEntity(),UserUtil.getUser(request).getId());
            }
        });
    }

    @PostMapping("addBug")
    public ResponseResult<String> addBug(HttpServletRequest request, @RequestBody @Valid CdBug cdBug) {
        ResponseResult<String> result = new ResponseResult<>();

        CdUser user = UserUtil.getUser(request);
        cdBug.setOpenedBy(user.getId());
        cdBug.setOpenedName(user.getAccount());
        cdBug.setOpenedDate(new Date());
        cdBug.setStatus(BugStatusEnum.ACTIVE.getStatusName());
        cdBug.setDeleted(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdBug));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("delete")
    public ResponseResult<String> delete(HttpServletRequest request,@RequestBody CdBug cdBug) {
        ResponseResult<String> result = new ResponseResult<>();

        if (cdBug.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要删除的BUG");
            return result;
        }

        CdBug bug = new CdBug();
        bug.setId(cdBug.getId());
        ServiceResult serviceResult = targetService.queryOne(bug);
        if (serviceResult.isSuccess()) {
            cdBug = (CdBug) serviceResult.getResult();
            if (cdBug != null) {
                cdBug.setDeleted(CdTestTask.DEL);
                cdBug.setUpdate(true);

                serviceResult = targetService.save(Collections.singletonList(cdBug));
                if (serviceResult.isSuccess()) {
                    result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("BUG不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    @PostMapping("updateStatus")
    public ResponseResult<String> updateStatus(HttpServletRequest request,@RequestBody CdBug cdBug) {
        ResponseResult<String> result = new ResponseResult<>();
        CdBug query = new CdBug();
        query.setId(cdBug.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (serviceResult.isSuccess()) {
            query = (CdBug) serviceResult.getResult();
            if (query != null) {
                cdBug.setUpdate(true);
                CdUser user = UserUtil.getUser(request);
                cdBug.setLastEditedBy(user.getId());
                cdBug.setLastEditedName(user.getAccount());
                cdBug.setLastEditedDate(new Date());
                cdBug.setActivatedCount(query.getActivatedCount() == null ? 0 : query.getActivatedCount());

                BugStatusEnum status = BugStatusEnum.getStatus(cdBug.getStatus());
                buildFinalTestTask(cdBug,status,user);

                CdActionLog cdActionLog = ActionLogHelper.buildBugLog(user.getAccount(), query,
                        cdBug.getStatusDesc(), user.getId());
                cdActionLogService.save(Collections.singletonList(cdActionLog));

                serviceResult = targetService.save(Collections.singletonList(cdBug));
                if (serviceResult.isSuccess()) {
                    result.setResponseStatus(ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("BUG不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    private void buildFinalTestTask(CdBug bug, BugStatusEnum status, CdUser user) {
        switch (status) {
            case CLOSED:
                bug.setClosedBy(user.getId());
                bug.setClosedName(user.getAccount());
                bug.setClosedDate(new Date());
                break;
            case ACTIVE:
                bug.setActivatedCount((short) (bug.getActivatedCount() + 1));
                bug.setActivatedDate(new Date());
                bug.setOpenedBy(user.getId());
                bug.setOpenedName(user.getAccount());
                bug.setOpenedDate(new Date());
                break;

            case RESOLVED:
                bug.setResolvedBy(user.getId());
                bug.setResolvedName(user.getAccount());
                bug.setResolvedDate(new Date());
                break;
        }
    }

    @PostMapping("assigned")
    public ResponseResult<String> assigned(HttpServletRequest request, @RequestBody CdBug cdBug) {
        ResponseResult<String> result = new ResponseResult<>();

        if (cdBug.getId() == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("请选择要指派的BUG");
            return result;
        }

        CdBug query = new CdBug();
        query.setId(cdBug.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (!serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
            return result;
        }

        query = (CdBug) serviceResult.getResult();
        if (query == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("BUG不存在");
            return result;
        }

        CdUser user = UserUtil.getUser(request);
        cdBug.setLastEditedBy(user.getId());
        cdBug.setLastEditedName(user.getAccount());
        cdBug.setLastEditedDate(new Date());
        cdBug.setUpdate(true);
        cdBug.setAssignedDate(new Date());

        serviceResult = targetService.save(Collections.singletonList(cdBug));
        if (serviceResult.isSuccess()) {

            CdActionLog cdActionLog = ActionLogHelper.buildBugLog(cdBug.getAssignedByName(), query,
                    "指派", cdBug.getAssignedBy());
            cdActionLogService.save(Collections.singletonList(cdActionLog));
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
