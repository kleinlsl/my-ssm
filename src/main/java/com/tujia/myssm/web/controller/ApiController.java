package com.tujia.myssm.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: songlinl
 * @create: 2025/2/24 16:49
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @GetMapping("/data")
    public String data() {
        return "success";
    }


}
