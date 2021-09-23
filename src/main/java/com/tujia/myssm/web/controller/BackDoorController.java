package com.tujia.myssm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.myssm.base.BizTemplate;
import com.tujia.myssm.base.BizTemplatePool;
import com.tujia.myssm.base.exception.BizException;
import com.tujia.myssm.base.monitor.Monitors;
import com.tujia.myssm.web.aop.UserIdentify;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/09/08 16:59
 */
@Slf4j
@RestController
public class BackDoorController {

    @GetMapping("/biz/template")
    public String testBizTemplate() {
        return new BizTemplate<String>(Monitors.BackDoorController_testBizTemplate) {

            @Override
            protected void checkParams() throws BizException {

            }

            @Override
            protected String process() throws Exception {
                log.info("[BackDoorController.testBizTemplate] 处理了，{},{}", this, System.identityHashCode(this));
                return null;
            }

        }.execute();
    }

    @GetMapping("/biz/template/a")
    public String testBizTemplateA() {
        return BizTemplatePool.get(Monitors.BackDoorController_testBizTemplateA, () -> new BizTemplate<String>() {
            @Override
            protected void checkParams() throws BizException {
                log.info("[BackDoorController.testBizTemplateA] checkParams 处理了，{},{}", this,
                        System.identityHashCode(this));
            }

            @Override
            protected String process() throws Exception {
                log.info("[BackDoorController.testBizTemplateA] process 处理了，{},{}", this,
                        System.identityHashCode(this));
                return "成功了： " + this.hashCode();
            }
        }).execute();
    }

    @GetMapping("/biz/template/C")
    public String testBizTemplateC() {
        return BizTemplatePool.get(Monitors.BackDoorController_testBizTemplateC, () -> new BizTemplate<String>() {
            @Override
            protected void checkParams() throws BizException {
                log.info("[BackDoorController.testBizTemplateC] checkParams 处理了，{},{}", this,
                        System.identityHashCode(this));
            }

            @Override
            protected String process() throws Exception {
                log.info("[BackDoorController.testBizTemplateC] process 处理了，{},{}", this,
                        System.identityHashCode(this));
                return "成功了： " + this.hashCode();
            }
        }).execute();
    }

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
