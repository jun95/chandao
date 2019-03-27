package com.selfboot.chandao.service;

import com.alibaba.fastjson.JSON;
import com.selfboot.chandao.base.BaseSpringTest;
import com.selfboot.chandao.domain.CdUPermission;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by 87570 on 2019/3/27.
 */
public class PermissionServiceTest extends BaseSpringTest {

    @Autowired
    private PermissionService permissionService;

    @Test
    public void testGetMenu() {
        List<CdUPermission> cdUPermissions = permissionService.loadMenu(1L);
        System.out.println(JSON.toJSONString(cdUPermissions));
    }
}
