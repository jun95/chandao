package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.*;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.domain.CdGroup;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.domain.CdUserGroup;
import com.selfboot.chandao.listener.DataCallback;
import com.selfboot.chandao.persist.CrudService;
import com.selfboot.chandao.persist.DataCallbackParam;
import com.selfboot.chandao.service.CdGroupService;
import com.selfboot.chandao.service.CdUserGroupService;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

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
        cdGroup.setDeleted(1);
        return getRecords(cdGroup,offset,limit);
    }

    @PostMapping("getGroupTotalRecord")
    public ResponseResult<List<CdGroup>> getGroupTotalRecord() {
        ResponseResult<List<CdGroup>> result = new ResponseResult<>();
        CdGroup cdGroup = new CdGroup();
        cdGroup.setDeleted(1);
        ServiceResult serviceResult = targetService.queryList(cdGroup);
        if (serviceResult.isSuccess()) {
            result.setResult((List<CdGroup>) serviceResult.getResult());
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }
        return result;
    }

    @GetMapping("getGroupMemberRecords")
    public Map<String,Object> getGroupMemberRecords(CdUserGroup cdUserGroup, @RequestParam(value = "groupId",required = false) String groupId,
                                              @RequestParam(value = "offset",required = false) Integer offset,
                                              @RequestParam(value = "limit",required = false) Integer limit) {
        if (!StringUtils.isBlank(groupId)) {
            cdUserGroup.setGroupId(Long.parseLong(groupId));
        }

        if (cdUserGroup.getProjectId() == null) {
            return getRecords(cdUserGroupService,cdUserGroup,offset,limit);
        } else {
            return getRecords(cdUserGroupService, cdUserGroup, offset, limit, new DataCallback<CdUserGroup>() {
                @Override
                public List<CdUserGroup> onPushData(CrudService crudService, DataCallbackParam<CdUserGroup> params) {
                    CdUserGroupService service = (CdUserGroupService) crudService;
                    List<CdUserGroup> groupList = service.getListByProjectId(params.getEntity().getProjectId());
                    return groupList;
                }
            });
        }
    }

    @PostMapping("addGroup")
    public ResponseResult<String> addGroup(HttpServletRequest request, @RequestBody @Valid CdGroup cdGroup) {
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
