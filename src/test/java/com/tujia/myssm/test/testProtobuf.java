package com.tujia.myssm.test;

import java.io.IOException;
import org.junit.Test;
import com.tujia.myssm.cache.CacheModels;
import com.tujia.myssm.utils.ProtoJsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2021/10/22 21:32
 */
public class testProtobuf {

    @Test
    public void testStudent() throws IOException {
        CacheModels.Student student = CacheModels.Student.newBuilder().setScore(CacheModels.BDecimal.newBuilder().setScale(1).build()).build();

        System.out.println("student = " + student);

        String json = ProtoJsonUtils.toJson(student);
        System.out.println("json = " + json);
    }
}
