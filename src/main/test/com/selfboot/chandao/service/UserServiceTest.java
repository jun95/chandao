package com.selfboot.chandao.service;

import com.github.pagehelper.PageInfo;
import com.selfboot.chandao.base.BaseSpringTest;
import com.selfboot.chandao.common.Pager;
import com.selfboot.chandao.domain.CdUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * Created by hwj on 2019/3/14.
 */
public class UserServiceTest extends BaseSpringTest {

    @Resource
    private CdUserService cdUserService;

    @Value("${server.address}")
    private String address;

    @Test
    public void testGetPage() {
        PageInfo<CdUser> userPageInfo = cdUserService.findPage(null, new Pager());
        System.out.println(userPageInfo);
    }

    @Test
    public void testGetProp() {
        System.out.println(address);
    }

}
