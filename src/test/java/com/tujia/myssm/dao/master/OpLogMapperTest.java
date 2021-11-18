package com.tujia.myssm.dao.master;

import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.enums.EnumOpType;
import com.tujia.myssm.api.model.DefaultOpLogMark;
import com.tujia.myssm.api.model.OpLog;
import com.tujia.myssm.api.model.OpLogDetail;
import com.tujia.myssm.common.utils.JsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2021/11/18 10:54
 */
public class OpLogMapperTest extends BaseTest {

    @Resource
    private OpLogMapper opLogMapper;

    @Test
    public void testInsert() {
        OpLog opLog = new OpLog();
        opLog.setType(EnumOpType.None.getId());
        opLog.setOperator("system");
        opLog.setDetail(OpLogDetail.builder().summary("summary").markType(1)
                                   .mark(DefaultOpLogMark.builder().mark("mark").build()).build());
        int count = opLogMapper.insert(opLog);
        Assert.assertTrue(count == 1);
    }

    @Test
    public void testSelectById() {
        OpLog opLog = opLogMapper.selectById(2L);
        System.out.println("JsonUtils.toJson(opLog) = " + JsonUtils.toJson(opLog));
    }
}