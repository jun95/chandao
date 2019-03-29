package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ProjectStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdProjectService;
import com.selfboot.chandao.service.CdRequirementService;
import com.selfboot.chandao.util.DateUtil;
import com.selfboot.chandao.util.UserUtil;
import com.selfboot.chandao.vo.ProjectVO;
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
 * Created by hwj on 2019/3/21.
 */
@RestController
@RequestMapping("project")
public class CdProjectController extends BaseController<CdProject, CdProjectService> {

    @Autowired
    private CdRequirementService cdRequirementService;

    @GetMapping("getProjectRecords")
    public Map<String,Object> getUserRecords(HttpServletRequest request,CdProject cdProject,
                                             @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdProject.setId(Long.parseLong(id));
        }
        cdProject.setDeleted(1);
        return getRecords(cdProject, offset, limit, new DataCallback<CdProject>() {
            @Override
            public List<CdProject> onPushData(CrudService crudService, DataCallbackParam<CdProject> params) {
                return targetService.selectListByGroup(params.getEntity(),UserUtil.getUser(request).getId());
            }
        });
    }

    @PostMapping("getProjectTotalRecord")
    public ResponseResult<List<CdProject>> getGroupTotalRecord(HttpServletRequest request) {
        ResponseResult<List<CdProject>> result = new ResponseResult<>();
        CdProject cdProject = new CdProject();
        cdProject.setDeleted(1);

        Long userId = UserUtil.getUser(request).getId();
        List<CdProject> list = targetService.selectUnCloseProject(cdProject,userId);
        result.setResult(list);
        result.setResponseStatus(ResponseStatus.OK);
        return result;
    }

    @PostMapping("updateStatus")
    public ResponseResult<String> updateStatus(HttpServletRequest request,@RequestBody @Valid ProjectVO projectVO) {
        ResponseResult<String> result = new ResponseResult<>();
        CdProject cdProject = new CdProject();
        cdProject.setId(projectVO.getId());
        ServiceResult serviceResult = targetService.queryOne(cdProject);
        if (serviceResult.isSuccess()) {
            cdProject = (CdProject) serviceResult.getResult();
            if (cdProject != null) {
                cdProject.setStatus(projectVO.getStatus());
                cdProject.setUpdate(true);

                serviceResult = targetService.save(Collections.singletonList(cdProject));

                updateRequirement(cdProject);

                if (serviceResult.isSuccess()) {
                    result.setResponseStatus(ResponseStatus.OK);
                    return result;
                }
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
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

    private void updateRequirement(CdProject cdProject) {
        CdRequirement cdRequirement = new CdRequirement();
        cdRequirement.setUpdate(true);
        cdRequirement.setProjectId(cdProject.getId());
        cdRequirement.setStatus(cdProject.getStatus());

        cdRequirementService.updateByProjectId(cdRequirement);
    }


    @PostMapping("addProject")
    public ResponseResult<String> addProject(HttpServletRequest request,@RequestBody @Valid CdProject cdProject) {
        ResponseResult<String> result = new ResponseResult<>();

        Date begin = cdProject.getBegin();
        Date end = cdProject.getEnd();
        int days = DateUtil.dayDiff(end, begin);

        //日期不符合要求
        if (days <= 0) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("开始日期需小于结束日期");
            return result;
        }

        cdProject.setDays((short) days);
        CdUser user = UserUtil.getUser(request);
        cdProject.setCreateBy(user.getId());
        cdProject.setCreateName(user.getAccount());
        cdProject.setCreateDate(new Date());
        cdProject.setStatus(ProjectStatusEnum.WAIT.getStatusName());
        cdProject.setDeleted(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdProject));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("showDetail")
    public ResponseResult<CdProject> showDetail(HttpServletRequest request, @RequestBody CdProject cdProject) {
        ResponseResult<CdProject> result = new ResponseResult<>();

        if (cdProject.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要查看的项目");
            return result;
        }

        CdProject project = new CdProject();
        project.setId(cdProject.getId());
        ServiceResult serviceResult = targetService.queryOne(project);
        if (serviceResult.isSuccess()) {
            cdProject = (CdProject) serviceResult.getResult();
            if (cdProject != null) {
                result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
                result.setResult(cdProject);
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

    @PostMapping("edit")
    public ResponseResult<CdProject> edit(HttpServletRequest request, @RequestBody CdProject cdProject) {
        ResponseResult<CdProject> result = new ResponseResult<>();

        if (cdProject.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要编辑的项目");
            return result;
        }

        CdProject project = new CdProject();
        project.setId(cdProject.getId());
        ServiceResult serviceResult = targetService.queryOne(project);
        if (serviceResult.isSuccess()) {
            project = (CdProject) serviceResult.getResult();
            if (project != null) {
                cdProject.setUpdate(true);
                targetService.save(Collections.singletonList(cdProject));
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
