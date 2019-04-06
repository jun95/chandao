package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ProjectStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.exception.GlobalException;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdProjectService;
import com.selfboot.chandao.service.CdRequirementService;
import com.selfboot.chandao.util.DateUtil;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/22.
 */
@RestController
@RequestMapping("require")
public class CdRequirementController extends BaseController<CdRequirement, CdRequirementService> {

    @Autowired
    private CdProjectService cdProjectService;

    /**
     * 获取需求列表
     * @return
     */
    @GetMapping("getRequireRecords")
    public Map<String,Object> getRequireRecords(HttpServletRequest request,CdRequirement cdRequirement,
                                             @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdRequirement.setId(Long.parseLong(id));
        }
        cdRequirement.setDeleted(1);
        if (UserUtil.isAdmin(request)) {
            return getRecords(cdRequirement, offset, limit);
        }

        return getRecords(cdRequirement, offset, limit, new DataCallback<CdRequirement,CdRequirement>() {
            @Override
            public List<CdRequirement> onPushData(CrudService crudService, DataCallbackParam<CdRequirement> params) {
                return targetService.selectListByProject(params.getEntity(),UserUtil.getUser(request).getId());
            }
        });
    }

    /**
     * 通过项目id获取需求列表
     * @param cdRequirement
     * @return
     */
    @PostMapping("getRequireTotalRecordByProjectId")
    public ResponseResult<List<CdRequirement>> getRequireTotalRecordByProjectId(@RequestBody CdRequirement cdRequirement) {
        ResponseResult<List<CdRequirement>> result = new ResponseResult<>();
        if (cdRequirement.getProjectId() == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("项目id不能为空");
            return result;
        }
        cdRequirement.setDeleted(1);
        ServiceResult serviceResult = targetService.queryList(cdRequirement);
        if (serviceResult.isSuccess()) {
            result.setResult((List<CdRequirement>) serviceResult.getResult());
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 新增需求
     * @param request
     * @param cdRequirement
     * @return
     * @throws GlobalException
     */
    @RequiresRoles(value={"管理员", "项目经理","需求人员"},logical = Logical.OR)
    @PostMapping("addRequire")
    public ResponseResult<String> addRequire(HttpServletRequest request, @RequestBody @Valid CdRequirement cdRequirement) throws GlobalException {
        ResponseResult<String> result = new ResponseResult<>();

        Date begin = cdRequirement.getBegin();
        Date end = cdRequirement.getEnd();
        int days = DateUtil.dayDiff(end, begin);

        //日期不符合要求
        if (days <= 0) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("开始日期需小于结束日期");
            return result;
        }

        Long projectId = cdRequirement.getProjectId();
        CdProject entity = new CdProject();
        entity.setId(projectId);
        ServiceResult serviceResult1 = cdProjectService.queryOne(entity);
        if (serviceResult1.isSuccess()) {
            entity = (CdProject) serviceResult1.getResult();
            ProjectStatusEnum status = ProjectStatusEnum.getStatus(entity.getStatus());
            if (status != ProjectStatusEnum.WAIT) {
                throw new GlobalException("只有当项目待开始时才允许增加需求");
            }
            if (!checkTimeScope(entity,begin.getTime(),end.getTime())) {  //不在项目时间范围内
                throw new GlobalException("需求的时间范围需在项目的时间范围内");
            }
        } else {
            throw new GlobalException("项目不存在");
        }

        CdUser user = UserUtil.getUser(request);
        cdRequirement.setCreateBy(user.getId());
        cdRequirement.setCreateName(user.getAccount());
        cdRequirement.setCreateDate(new Date());
        cdRequirement.setStatus(ProjectStatusEnum.WAIT.getStatusName());
        cdRequirement.setDeleted(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdRequirement));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 是否在项目时间范围内
     * @param entity
     * @param start
     * @param end
     */
    private boolean checkTimeScope(CdProject entity, long start, long end) {
        long compareStart = entity.getBegin().getTime();
        long compareEnd = entity.getEnd().getTime();

        if (compareEnd > start && compareStart < end) {  //说明有重叠
            return true;
        }
        return false;
    }

    /**
     * 更新需求状态
     * @param request
     * @param cdRequirement
     * @return
     */
    @RequiresRoles(value={"管理员", "项目经理","需求人员"},logical = Logical.OR)
    @PostMapping("updateStatus")
    public ResponseResult<String> updateStatus(HttpServletRequest request,@RequestBody CdRequirement cdRequirement) {
        ResponseResult<String> result = new ResponseResult<>();
        CdRequirement query = new CdRequirement();
        query.setId(cdRequirement.getId());
        ServiceResult serviceResult = targetService.queryOne(query);
        if (serviceResult.isSuccess()) {
            query = (CdRequirement) serviceResult.getResult();
            if (query != null) {
                cdRequirement.setUpdate(true);

                ProjectStatusEnum status = ProjectStatusEnum.getStatus(cdRequirement.getStatus());
                buildFinalRequirement(cdRequirement,status,UserUtil.getUser(request));

                serviceResult = targetService.save(Collections.singletonList(cdRequirement));
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

    private void buildFinalRequirement(CdRequirement cdRequirement, ProjectStatusEnum status, CdUser user) {
        switch (status) {
            case DONE:
                cdRequirement.setClosedBy(user.getId());
                cdRequirement.setClosedName(user.getAccount());
                cdRequirement.setClosedDate(new Date());
                break;
        }
    }

    /**
     * 删除需求
     * @param request
     * @param cdRequirement
     * @return
     */
    @RequiresRoles(value={"管理员", "项目经理","需求人员"},logical = Logical.OR)
    @PostMapping("delete")
    public ResponseResult<String> delete(HttpServletRequest request,@RequestBody CdRequirement cdRequirement) {
        ResponseResult<String> result = new ResponseResult<>();

        if (cdRequirement.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要删除的需求");
            return result;
        }

        CdTestTask testTask = new CdTestTask();
        testTask.setId(cdRequirement.getId());
        ServiceResult serviceResult = targetService.queryOne(cdRequirement);
        if (serviceResult.isSuccess()) {
            cdRequirement = (CdRequirement) serviceResult.getResult();
            if (cdRequirement != null) {

                if (ProjectStatusEnum.getStatus(cdRequirement.getStatus()) != ProjectStatusEnum.WAIT) {
                    result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                    result.setMessage("需求只有在待开始状态的时候能删除");
                    return result;
                }

                cdRequirement.setDeleted(CdTestTask.DEL);
                cdRequirement.setUpdate(true);

                serviceResult = targetService.save(Collections.singletonList(cdRequirement));
                if (serviceResult.isSuccess()) {
                    result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            } else {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
                result.setMessage("需求不存在");
            }
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    /**
     * 展示需求详情
     * @param request
     * @param cdRequirement
     * @return
     */
    @PostMapping("showDetail")
    public ResponseResult<CdRequirement> showDetail(HttpServletRequest request, @RequestBody CdRequirement cdRequirement) {
        ResponseResult<CdRequirement> result = new ResponseResult<>();

        if (cdRequirement.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要查看的项目");
            return result;
        }

        CdRequirement requirement = new CdRequirement();
        requirement.setId(cdRequirement.getId());
        ServiceResult serviceResult = targetService.queryOne(requirement);
        if (serviceResult.isSuccess()) {
            cdRequirement = (CdRequirement) serviceResult.getResult();
            if (cdRequirement != null) {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                result.setResult(cdRequirement);
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
     * 编辑需求
     * @param request
     * @param cdRequirement
     * @return
     */
    @RequiresRoles(value={"管理员", "项目经理","需求人员"},logical = Logical.OR)
    @PostMapping("edit")
    public ResponseResult<CdProject> edit(HttpServletRequest request, @RequestBody CdRequirement cdRequirement) {
        ResponseResult<CdProject> result = new ResponseResult<>();

        if (cdRequirement.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要编辑的项目");
            return result;
        }

        CdRequirement requirement = new CdRequirement();
        requirement.setId(cdRequirement.getId());
        ServiceResult serviceResult = targetService.queryOne(requirement);
        if (serviceResult.isSuccess()) {
            requirement = (CdRequirement) serviceResult.getResult();
            if (requirement != null) {
                cdRequirement.setUpdate(true);
                targetService.save(Collections.singletonList(cdRequirement));
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
