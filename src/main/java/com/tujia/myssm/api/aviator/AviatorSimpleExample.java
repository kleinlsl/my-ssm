package com.tujia.myssm.api.aviator;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;

/**
 *
 * @author: songlinl
 * @create: 2022/12/26 14:34
 */
public class AviatorSimpleExample {
    public static void main(String[] args) {
        //        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        //        System.out.println(result);

        String name = "唐简";
        Map<String, Object> env = new HashMap<>();
        env.put("name", name);
        String result = (String) AviatorEvaluator.execute(" 'Hello ' + name ", env);
        System.out.println(result);

        String expression = "a    >    b || a   <    b";
        Map<String, Object> envm = Maps.newHashMap();
        envm.put("a", 1);
        envm.put("b", 2);
        Boolean b = (Boolean) AviatorEvaluator.execute(expression, envm);
        System.out.println(b);

        System.out.println("b = " + LocalTime.now());
    }

}
