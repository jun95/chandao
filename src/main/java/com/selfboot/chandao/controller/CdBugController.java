package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.BugStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdActionLog;
import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdActionLogService;
import com.selfboot.chandao.service.CdBugService;
import com.selfboot.chandao.service.RoleService;
import com.selfboot.chandao.util.ActionLogHelper;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by 87570 on 2019/3/23.
 */
@RestController
@RequestMapping("bug")
public class CdBugController extends BaseController<CdBug,CdBugService> {

    @Autowired
    private CdActionLogService cdActionLogService;

    @Resource
    private RoleService roleService;

    /**
     * 获取BUG列表
     * @return
     */
    @GetMapping("getBugRecords")
    public Map<String,Object> getBugRecords(HttpServletRequest request,CdBug cdBug,
                                            @RequestParam(value = "id",required = false) String id,
                                                 @RequestParam(value = "offset",required = false) Integer offset,
                                                 @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdBug.setId(Long.parseLong(id));
        }
        cdBug.setDeleted(1);

        if (UserUtil.isAdmin(request)) {
            return getRecords(cdBug, offset, limit);
        }

        return getRecords(cdBug, offset, limit, new DataCallback<CdBug>() {
            @Override
            public List<CdBug> onPushData(CrudService crudService, DataCallbackParam<CdBug> params) {
                return targetService.selectListByProject(params.getEntity(),UserUtil.getUser(request).getId());
            }
        });
    }

    /**
     * 新增BUG
     * @param request
     * @param cdBug
     * @return
     */
    @RequiresRoles(value={"管理员", "测试人员"},logical = Logical.OR)
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

    /**
     * 删除bug
     * @param request
     * @param cdBug
     * @return
     */
    @RequiresRoles(value={"管理员", "测试人员"},logical = Logical.OR)
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

    /**
     *更新bug状态
     * @param request
     * @param cdBug
     * @return
     */
    @RequiresRoles(value={"管理员", "测试人员","开发人员"},logical = Logical.OR)
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
                //只有解决开发人员才能点击
                if (status != BugStatusEnum.RESOLVED) {
                    Set<String> roles = roleService.findRoleByUserId(user.getId());
                    if (roles.contains("开发人员")) {
                        throw new AuthorizationException();
                    }
                }

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

    /**
     * 指派bug
     * @param request
     * @param cdBug
     * @return
     */
    @RequiresRoles(value={"管理员", "测试人员","开发人员"},logical = Logical.OR)
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

            if (query.getAssignedBy() == null) {
                CdActionLog cdActionLog = new CdActionLog();
                cdActionLog.setObjectType("bug");
                cdActionLog.setObjectId(query.getId());

                cdActionLog = cdActionLogService.selectLatestRecord(cdActionLog);
                query.setAssignedBy(cdActionLog.getAssignedBy());
            }

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

    /**
     * 查看bug详情
     * @param request
     * @param cdBug
     * @return
     */
    @PostMapping("showDetail")
    public ResponseResult<CdBug> showDetail(HttpServletRequest request, @RequestBody CdBug cdBug) {
        ResponseResult<CdBug> result = new ResponseResult<>();

        if (cdBug.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要查看的项目");
            return result;
        }

        CdBug bug = new CdBug();
        bug.setId(cdBug.getId());
        ServiceResult serviceResult = targetService.queryOne(bug);
        if (serviceResult.isSuccess()) {
            cdBug = (CdBug) serviceResult.getResult();
            if (cdBug != null) {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                result.setResult(cdBug);
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
     * 编辑bug
     * @param request
     * @param cdBug
     * @return
     */
    @RequiresRoles(value={"管理员", "测试人员"},logical = Logical.OR)
    @PostMapping("edit")
    public ResponseResult<CdBug> edit(HttpServletRequest request, @RequestBody CdBug cdBug) {
        ResponseResult<CdBug> result = new ResponseResult<>();

        if (cdBug.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要编辑的项目");
            return result;
        }

        CdBug bug = new CdBug();
        bug.setId(cdBug.getId());
        ServiceResult serviceResult = targetService.queryOne(bug);
        if (serviceResult.isSuccess()) {
            bug = (CdBug) serviceResult.getResult();
            if (bug != null) {
                cdBug.setUpdate(true);
                targetService.save(Collections.singletonList(cdBug));
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
}
