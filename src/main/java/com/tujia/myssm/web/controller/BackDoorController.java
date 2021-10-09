package com.tujia.myssm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.myssm.web.aop.UserIdentify;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/09/08 16:59
 */
@Slf4j
@RestController
@RequestMapping("/backDoor")
public class BackDoorController extends BaseController {

    @GetMapping("/userIdentify")
    @UserIdentify(mustLogin = true)
    public String userIdentify() {
        return "success";
    }

    @GetMapping("/userIdentifyEx")
    @UserIdentify(mustLogin = true)
    public String userIdentifyEx() {
        throw new RuntimeException("发生异常了");
    }

}
