package com.tujia.myssm.service.impl;

import org.springframework.stereotype.Service;
import com.tujia.myssm.service.CommonService;

/**
 *
 * @author: songlinl
 * @create: 2021/09/13 14:22
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public void sayHello() {
        System.err.println("hello = " + "hello");
    }
}
