package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdProject;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * Created by hwj on 2019/3/21.
 */
@RestController
@RequestMapping("project")
public class CdProjectController extends BaseController<CdProject, CdProjectService> {


    @GetMapping("getProjectRecords")
    public Map<String,Object> getUserRecords(CdProject cdProject, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdProject.setId(Long.parseLong(id));
        }
        return getRecords(cdProject, offset, limit);
    }

    @PostMapping("addProject")
    public ResponseResult<String> addProject(@RequestBody @Valid CdProject cdProject) {
        ResponseResult<String> result = new ResponseResult<>();

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdProject));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
