package com.tujia.myssm.api.aviator;

import java.util.Map;
import org.junit.Test;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;

/**
 *
 * @author: songlinl
 * @create: 2022/12/26 15:20
 */
public class AviatorSimpleExampleTest {

    @Test
    public void test() {
        String expression = "a>b || a<b || include(seq.set(99, 100, 101),c)";
        Map<String, Object> envm = Maps.newHashMap();
        envm.put("a", 1);
        envm.put("b", 2);
        envm.put("c", 0);
        Boolean b = (Boolean) AviatorEvaluator.execute(expression, envm);
        System.out.println(b);
    }
}