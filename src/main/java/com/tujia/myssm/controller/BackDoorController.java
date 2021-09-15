package com.tujia.myssm.controller;

import com.tujia.myssm.base.BizTemplate;
import com.tujia.myssm.base.exception.BizException;
import com.tujia.myssm.base.monitor.Monitors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: songlinl
 * @create: 2021/09/08 16:59
 */
@Slf4j
@RestController
public class BackDoorController {

    @GetMapping("/biz/template")
    public String testBizTemplate() {
        return new BizTemplate<String>(Monitors.BackDoorController_testBizTemplate){

            @Override
            protected void checkParams() throws BizException {

            }

            @Override
            protected String process() throws Exception {
                log.info("[BackDoorController.testBizTemplate] 处理了，{},{}",this,System.identityHashCode(this));
                return null;
            }
        }.intern().execute();
    }

    @GetMapping("/biz/template/a")
    public String testBizTemplateA() {
        return new BizTemplate<String>(Monitors.BackDoorController_testBizTemplateA) {

            @Override
            protected void checkParams() throws BizException {

            }

            @Override
            protected String process() throws Exception {
                log.info("[BackDoorController.testBizTemplate] 处理了，{},{}", this, System.identityHashCode(this));
                return null;
            }
        }.intern().execute();
    }
}