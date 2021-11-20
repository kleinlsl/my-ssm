package com.tujia.myssm.web.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.myssm.service.impl.LogBackServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/11/21 01:06
 */
@Slf4j
@RestController
@RequestMapping(value = "logback")
public class TestLogBackController {
    @Resource
    private LogBackServiceImpl logBackService;

    @GetMapping(value = "testLogBackInfo")
    public String testLogbackInfo() {
        logBackService.info();
        return "success";
    }
}
