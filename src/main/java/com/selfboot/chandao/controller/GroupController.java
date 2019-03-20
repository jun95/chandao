package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdGroup;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.domain.CdUserGroup;
import com.selfboot.chandao.service.CdGroupService;
import com.selfboot.chandao.service.CdUserGroupService;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
@RestController
@RequestMapping("group")
public class GroupController extends BaseController<CdGroup, CdGroupService> {

    @Resource
    private CdUserGroupService cdUserGroupService;

    @GetMapping("getGroupRecords")
    public Map<String,Object> getGroupRecords(CdGroup cdGroup, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit",required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdGroup.setId(Long.parseLong(id));
        }
        return getRecords(cdGroup,offset,limit);
    }

    @GetMapping("getGroupMemberRecords")
    public Map<String,Object> getGroupMemberRecords(CdUserGroup cdUserGroup, @RequestParam(value = "id",required = false) String id,
                                              @RequestParam(value = "offset",required = false) Integer offset,
                                              @RequestParam(value = "limit",required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdUserGroup.setId(Long.parseLong(id));
        }
        return getRecords(cdUserGroupService,cdUserGroup,offset,limit);
    }

    @PostMapping("addGroup")
    public ResponseResult<String> addGroup(HttpServletRequest request, @RequestBody CdGroup cdGroup) {
        ResponseResult<String> result = new ResponseResult<>();
        CdUser user = UserUtil.getUser(request);

        cdGroup.setCreateBy(user.getId());
        cdGroup.setCreateName(user.getAccount());
        cdGroup.setCreateTime(new Date());

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdGroup));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("addGroupMember")
    public ResponseResult<String> addGroupMember(HttpServletRequest request,
                                                 @RequestBody @Valid List<CdUserGroup> cdUserGroupList) {
        ResponseResult<String> result = new ResponseResult<>();

        CdUser user = UserUtil.getUser(request);
        for (CdUserGroup cdUserGroup : cdUserGroupList) {
            cdUserGroup.setCreateTime(new Date());
            cdUserGroup.setCreateBy(user.getId());
            cdUserGroup.setCreateName(user.getAccount());
        }

        ServiceResult serviceResult = cdUserGroupService.save(cdUserGroupList);
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
