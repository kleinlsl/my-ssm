package com.tujia.myssm.controller;

import com.google.common.base.Preconditions;
import com.tujia.framework.api.APIResponse;
import com.tujia.framework.utility.JSONUtils;
import com.tujia.myssm.bean.CityCashBack;
import com.tujia.myssm.service.CityCashBackService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: songlinl
 * @create: 2021/08/06 15:52
 */
@RestController
@RequestMapping("/activity/cashback/ctrip/train/")
public class CityCashBackController {
    private final Logger logger = LoggerFactory.getLogger(CityCashBackController.class);

    @Resource
    private CityCashBackService cityCashBackService;

    @ResponseBody
    @RequestMapping(value = "/tips", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse<List<CityCashBack>> cashBackTips(@RequestBody CityCashBack req) {
//        req.setDate(new Date());
        List<CityCashBack> res = cityCashBackService.query(req);
        return APIResponse.returnSuccess(res);
    }
}
