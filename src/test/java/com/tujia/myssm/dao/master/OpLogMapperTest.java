package com.tujia.myssm.dao.master;

import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.Base64Utils;
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

    @Test
    public void test() {
        String src = new String(Base64Utils.decodeFromString(
                "L3JuX2JuYi9tYWluLmpzP0NSTk1vZHVsZU5hbWU9VHVKaWFBcHAmQ1JOVHlwZT0xJmluaXRpYWxQYWdlPVNlYXJjaExpc3QmZnJvbVBhZ2U9dHJhaW5UYXNrJnBhZ2VOYW1lPXRyYWluVGFzayZ0cmFpblRhc2tJZD0xMDAwMyZ1cmw9aHR0cHMlM0ElMkYlMkZtLnR1amlhLmNvbSUyRmdvbmd5dSUyRmJlaWppbmclMkY="));
        System.out.println("src = " + src);
        src = new String(Base64Utils.decodeFromString(
                "L3JuX2JuYi9tYWluLmpzP0NSTk1vZHVsZU5hbWU9VHVKaWFBcHAmQ1JOVHlwZT0xJmluaXRpYWxQYWdlPVNlYXJjaExpc3QmZnJvbVBhZ2U9dHJhaW5UYXNrJnBhZ2VOYW1lPXRyYWluVGFzayZ0cmFpblRhc2tJZD0xMDAwNCZ1cmw9aHR0cHMlM0ElMkYlMkZtLnR1amlhLmNvbSUyRmdvbmd5dSUyRmJlaWppbmclMkY="));
        System.out.println("src = " + src);
    }

    @Test
    public void testInsertOrUpdate() {
        OpLog opLog = opLogMapper.selectById(2L);
        opLog.setOperator("songlinl");
        //        opLog.setCreateTime(LocalDateTime.now());
        int count = opLogMapper.insertOrUpdate(opLog);
        System.err.println("count = " + count);
    }
}