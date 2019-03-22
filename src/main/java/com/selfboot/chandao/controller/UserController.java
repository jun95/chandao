package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdUserService;
import com.selfboot.chandao.util.UserUtil;
import com.selfboot.chandao.vo.UserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * Created by 87570 on 2019/3/18.
 */
@RestController
@RequestMapping("user")
@Validated
public class UserController extends BaseController<CdUser, CdUserService> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("getUserInfo")
    public ResponseResult<UserInfoVO> getUserName(HttpServletRequest request) {
        ResponseResult<UserInfoVO> result = new ResponseResult<>(ResponseStatus.OK);
        CdUser user = UserUtil.getUser(request);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setAccount(user.getAccount());

        result.setResult(userInfoVO);
        return result;
    }

    @GetMapping("getUserRecords")
    public Map<String,Object> getUserRecords(CdUser cdUser,@RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdUser.setId(Long.parseLong(id));
        }
        return getRecords(cdUser, offset, limit);
    }

    @PostMapping("addMember")
    public ResponseResult<String> addMember(@RequestBody @Valid CdUser cdUser) {
        ResponseResult<String> result = new ResponseResult<>();

        Long count = targetService.queryCount(cdUser);
        if (count > 0) {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage("当前用户名已经被使用，请更换");
            return result;
        }

        cdUser.setPassword("b7797cce01b4b131b433b6acf4add449");
        cdUser.setDeleted(1);
        ServiceResult serviceResult = targetService.save(Collections.singletonList(cdUser));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("removeMember")
    public ResponseResult<String> removeMember(@RequestBody @NotEmpty(message = "请选择要禁用的记录") List<Long> ids) {
        ResponseResult<String> result = new ResponseResult<>();
        if (CollectionUtils.isEmpty(ids)) {
            result.setResponseStatus(ResponseStatus.ILLEGAL_ARG);
            result.setMessage("请选择一条记录");
            return result;
        }
        List<CdUser> cdUserList = new ArrayList<>();
        CdUser cdUser = null;
        for (Long id : ids) {
            cdUser = new CdUser();
            cdUser.setId(id);
            cdUser.setDeleted(CdUser.DEL);
            cdUser.setUpdate(true);
            cdUserList.add(cdUser);
        }

        ServiceResult serviceResult = targetService.save(cdUserList);
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
