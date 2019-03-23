package com.selfboot.chandao.controller;

import com.selfboot.chandao.domain.CdTestTask;
import com.selfboot.chandao.service.CdTestTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 87570 on 2019/3/23.
 */
@RestController
@RequestMapping("testtask")
public class CdTestTaskController extends BaseController<CdTestTask,CdTestTaskService> {

    @GetMapping("getTestTaskRecords")
    public Map<String,Object> getTestTaskRecords(CdTestTask cdTestTask, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdTestTask.setId(Long.parseLong(id));
        }
        return getRecords(cdTestTask, offset, limit);
    }


}
