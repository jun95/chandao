package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ProjectStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdRequirement;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdRequirementService;
import com.selfboot.chandao.util.DateUtil;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
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

    @GetMapping("getRequireRecords")
    public Map<String,Object> getUserRecords(CdRequirement cdRequirement, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdRequirement.setId(Long.parseLong(id));
        }
        return getRecords(cdRequirement, offset, limit);
    }

    @PostMapping("getRequireTotalRecordByProjectId")
    public ResponseResult<List<CdRequirement>> getGroupTotalRecord(@RequestBody CdRequirement cdRequirement) {
        ResponseResult<List<CdRequirement>> result = new ResponseResult<>();
        if (cdRequirement.getProjectId() == null) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("项目id不能为空");
            return result;
        }

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

    @PostMapping("addRequire")
    public ResponseResult<String> addRequire(HttpServletRequest request, @RequestBody @Valid CdRequirement cdRequirement) {
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
}
