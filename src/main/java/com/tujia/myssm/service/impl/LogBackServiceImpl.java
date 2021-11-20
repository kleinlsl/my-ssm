package com.tujia.myssm.service.impl;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/11/21 00:46
 */
@Service
@Slf4j
public class LogBackServiceImpl {

    public void info() {
        Exception e = new Exception("this is Exception！");
        log.info("e", e);
        log.warn("e", e);
        log.error("e", e);
    }
}
