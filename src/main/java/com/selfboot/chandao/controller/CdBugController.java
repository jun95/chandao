package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.BugStatusEnum;
import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdBugService;
import com.selfboot.chandao.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/23.
 */
@RestController
@RequestMapping("bug")
public class CdBugController extends BaseController<CdBug,CdBugService> {

    @GetMapping("getBugRecords")
    public Map<String,Object> getBugRecords(CdBug cdBug, @RequestParam(value = "id",required = false) String id,
                                                 @RequestParam(value = "offset",required = false) Integer offset,
                                                 @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdBug.setId(Long.parseLong(id));
        }
        cdBug.setDeleted(1);
        return getRecords(cdBug, offset, limit);
    }

    @PostMapping("addBug")
    public ResponseResult<String> addBug(HttpServletRequest request, @RequestBody @Valid CdBug cdBug) {
        ResponseResult<String> result = new ResponseResult<>();

        CdUser user = UserUtil.getUser(request);
        cdBug.setOpenedBy(user.getId());
        cdBug.setOpenedName(user.getAccount());
        cdBug.setOpenedDate(new Date());
        cdBug.setStatus(BugStatusEnum.ACTIVE.getStatusName());
        cdBug.setDeleted(1);

        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdBug));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.OK);
        } else {
            result.setResponseStatus(com.selfboot.chandao.common.ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
