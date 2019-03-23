package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ProjectStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdTaskService;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
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

    @GetMapping("getTaskRecords")
    public Map<String,Object> getTaskRecords(CdTask cdTask, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdTask.setId(Long.parseLong(id));
        }
        return getRecords(cdTask, offset, limit);
    }


    @PostMapping("addTask")
    public ResponseResult<String> addTask(HttpServletRequest request, @RequestBody @Valid CdTask cdTask) {
        ResponseResult<String> result = new ResponseResult<>();

        CdUser user = UserUtil.getUser(request);
        cdTask.setOpenedBy(user.getId());
        cdTask.setOpenedName(user.getAccount());
        cdTask.setOpenedDate(new Date());

        cdTask.setAssignedDate(new Date());

        cdTask.setStatus(ProjectStatusEnum.WAIT.getStatusName());
        cdTask.setDeleted(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdTask));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
