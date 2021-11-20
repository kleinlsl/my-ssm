package com.tujia.myssm.test;

import javax.annotation.Resource;
import org.junit.Test;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.service.impl.LogBackServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/11/21 00:42
 */
@Slf4j
public class testLogback extends BaseTest {

    @Resource
    private LogBackServiceImpl logBackService;

    @Test
    public void testLogInfo() {
        logBackService.info();
        System.out.println("log = " + log);
    }
}
