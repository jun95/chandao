package com.selfboot.chandao.controller;

import com.selfboot.chandao.domain.CdTask;
import com.selfboot.chandao.service.CdTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by hwj on 2019/3/22.
 */
@RestController
@RequestMapping("task")
public class CdTaskController extends BaseController<CdTask, CdTaskService> {

    @GetMapping("getTaskRecords")
    public Map<String,Object> getUserRecords(CdTask cdTask, @RequestParam(value = "id",required = false) String id,
                                             @RequestParam(value = "offset",required = false) Integer offset,
                                             @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdTask.setId(Long.parseLong(id));
        }
        return getRecords(cdTask, offset, limit);
    }


}
