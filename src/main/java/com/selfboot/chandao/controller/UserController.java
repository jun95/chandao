package com.selfboot.chandao.controller;

import com.selfboot.chandao.common.ResponseResult;
import com.selfboot.chandao.common.ResponseStatus;
import com.selfboot.chandao.common.ServiceResult;
import com.selfboot.chandao.domain.CdUser;
import com.selfboot.chandao.service.CdUserService;
import com.selfboot.chandao.util.UserUtil;
import com.selfboot.chandao.vo.UserInfoVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 87570 on 2019/3/18.
 */
@RestController
@RequestMapping("user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CdUserService cdUserService;

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
    public Map<String,Object> getUserRecords(CdUser cdUser,@RequestParam("id") String id) {
        Map<String,Object> responseContent = new HashedMap();
        long total = 0;
        List<CdUser> rows = null;
        if (!StringUtils.isBlank(id)) {
            cdUser.setId(Long.parseLong(id));
        }

        Map<String, Object> queryResult = cdUserService.selectUserRecord(cdUser, 0, 10);
        if (queryResult != null) {
            rows = (List<CdUser>) queryResult.get("data");
            total = (long) queryResult.get("total");
        }
        responseContent.put("rows", rows);
        responseContent.put("total",total);

        return responseContent;
    }

    @PostMapping("addMember")
    public ResponseResult<String> addMember(@RequestBody CdUser cdUser) {
        ResponseResult<String> result = new ResponseResult<>();
        cdUser.setPassword("b7797cce01b4b131b433b6acf4add449");
        cdUser.setDeleted(1);
        ServiceResult serviceResult = cdUserService.save(Collections.singletonList(cdUser));
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }

    @PostMapping("removeMember")
    public ResponseResult<String> removeMember(@RequestBody List<Long> ids) {
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

        ServiceResult serviceResult = cdUserService.save(cdUserList);
        if (serviceResult.isSuccess()) {
            result.setResponseStatus(ResponseStatus.OK);
        } else {
            result.setResponseStatus(ResponseStatus.ERROR);
            result.setMessage((String) serviceResult.getErrorMessage().get(0));
        }

        return result;
    }
}
