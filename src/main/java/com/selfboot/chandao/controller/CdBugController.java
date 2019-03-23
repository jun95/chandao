package com.selfboot.chandao.controller;

import com.selfboot.chandao.domain.CdBug;
import com.selfboot.chandao.service.CdBugService;
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
@RequestMapping("bug")
public class CdBugController extends BaseController<CdBug,CdBugService> {

    @GetMapping("getBugRecords")
    public Map<String,Object> getBugRecords(CdBug cdBug, @RequestParam(value = "id",required = false) String id,
                                                 @RequestParam(value = "offset",required = false) Integer offset,
                                                 @RequestParam(value = "limit" ,required = false) Integer limit) {
        if (!StringUtils.isBlank(id)) {
            cdBug.setId(Long.parseLong(id));
        }
        return getRecords(cdBug, offset, limit);
    }
}
