package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdURole;
import com.selfboot.chandao.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController<CdURole, RoleService> {

    @GetMapping("getRoleRecords")
    public Map<String,Object> getUserRecords(CdURole cdURole, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit",required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdURole.setId(Long.parseLong(id));
        }
        return getRecords(cdURole,offset,limit);
    }

    @PostMapping("addRole")
    public ResponseResult<String> addRole(@RequestBody @Valid CdURole cdURole) {
        ResponseResult<String> result = new ResponseResult<>();
        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdURole));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
