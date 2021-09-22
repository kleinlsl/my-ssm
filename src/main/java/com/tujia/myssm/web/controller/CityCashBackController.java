package com.tujia.myssm.web.controller;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.api.model.CityCashBack;
import com.tujia.myssm.service.CityCashBackService;

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
