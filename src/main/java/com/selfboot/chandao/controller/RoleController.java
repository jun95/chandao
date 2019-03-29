package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.domain.CdURole;
import com.selfboot.chandao.domain.CdURolePermission;
import com.selfboot.chandao.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
@RestController
@RequestMapping("role")
public class RoleController extends BaseController<CdURole, RoleService> {
    /**
     *获取角色列表
     * @return
     */
    @GetMapping("getRoleRecords")
    public Map<String,Object> getRoleRecords(CdURole cdURole, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit",required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdURole.setId(Long.parseLong(id));
        }
        //cdURole.setDeleted(1);
        return getRecords(cdURole,offset,limit);
    }

    /**
     * 新增角色
     * @param cdURole
     * @return
     */
    @PostMapping("addRole")
    public ResponseResult<String> addRole(@RequestBody @Valid CdURole cdURole) {
        ResponseResult<String> result = new ResponseResult<>();
        cdURole.setCreateTime(new Date());
        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdURole));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    /**
     * 分配权限
     * @param rolePermission
     * @return
     */
    @PostMapping("allotPermission")
    public ResponseResult<String> allotPermission(CdURolePermission rolePermission) {
        ResponseResult<String> result = new ResponseResult<>(ResponseStatus.OK);
        targetService.allotPermission(rolePermission);
        return result;
    }

    @RequestMapping("rolesWithSelected")
    public ResponseResult<List<CdURole>> rolesWithSelected(Integer uid){
        ResponseResult<List<CdURole>> result = new ResponseResult<>(ResponseStatus.OK);

        List<CdURole> resources = targetService.queryRoleListWithSelected(uid);
        result.setResult(resources);
        return result;
    }
}
