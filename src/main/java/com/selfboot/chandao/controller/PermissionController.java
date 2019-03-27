package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdUPermission;
import com.selfboot.chandao.exception.GlobalException;
import com.selfboot.chandao.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/26.
 */
@RestController
@RequestMapping("permission")
public class PermissionController extends BaseController<CdUPermission,PermissionService> {

    @GetMapping("getPermissionRecords")
    public Map<String,Object> getUserRecords(CdUPermission cdUPermission, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit",required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdUPermission.setId(Long.parseLong(id));
        }
        //cdURole.setDeleted(1);
        return getRecords(cdUPermission,offset,limit);
    }


    @PostMapping("getPermissionTotalRecord")
    public ResponseResult<List<CdUPermission>> getGroupTotalRecord() {
        ResponseResult<List<CdUPermission>> result = new ResponseResult<>();

        CdUPermission cdUPermission = new CdUPermission();
        cdUPermission.setType(1);

        List<CdUPermission> list = (List<CdUPermission>) targetService.queryList(cdUPermission).getResult();
        result.setResult(list);
        result.setResponseStatus(ResponseStatus.OK);
        return result;
    }

    @PostMapping("addPermission")
    public ResponseResult<String> addPermission(@RequestBody @Valid CdUPermission cdUPermission) throws GlobalException {
        ResponseResult<String> result = new ResponseResult<>();
        Long parentId = cdUPermission.getParentId();
        if (parentId == null) {
            if (cdUPermission.getType() == 2) {
                throw new GlobalException("按钮权限不能为父权限");
            }
            cdUPermission.setParentId(0L);
        }

        Integer sort = targetService.selectSort(cdUPermission);

        if (sort == null) {
            cdUPermission.setSort(1);
        } else {
            cdUPermission.setSort(++sort);
        }

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdUPermission));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("delete")
    public ResponseResult<String> delete(HttpServletRequest request, @RequestBody CdUPermission cdUPermission) {
        ResponseResult<String> result = new ResponseResult<>(ResponseStatus.OK);

        if (cdUPermission.getId() == null) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage("请选择要删除的权限");
            return result;
        }
        targetService.deletePermission(cdUPermission.getId());
        return result;
    }

    @PostMapping("resourcesWithSelected")
    public ResponseResult<List<CdUPermission>> resourcesWithSelected(Integer rid) {
        ResponseResult<List<CdUPermission>> result = new ResponseResult<>(ResponseStatus.OK);

        List<CdUPermission> resources = targetService.queryResourcesListWithSelected(rid);
        result.setResult(resources);
        return result;
    }
}
