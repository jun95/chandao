package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdGroup;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdGroupService;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by hwj on 2019/3/20.
 */
@RestController
@RequestMapping("group")
public class GroupController extends BaseController<CdGroup, CdGroupService> {

    @GetMapping("getGroupRecords")
    public Map<String,Object> getGroupRecords(CdGroup cdGroup, @RequestParam("id") String id,
                                             @RequestParam("offset") Integer offset,
                                             @RequestParam("limit") Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdGroup.setId(Long.parseLong(id));
        }
        return getRecords(cdGroup,offset,limit);
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
}
