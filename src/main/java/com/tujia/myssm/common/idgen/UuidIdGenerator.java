package com.tujia.myssm.common.idgen;

import java.util.UUID;
import com.tujia.myssm.base.exception.BizException;

/**
 *
 * @author: songlinl
 * @create: 2022/12/13 10:53
 */
public class UuidIdGenerator implements IdGenerator {

    @Override
    public Long nextId() {
        throw new BizException("方法未实现");
    }

    @Override
    public String nextCode() {
        return UUID.randomUUID().toString();
    }
}
