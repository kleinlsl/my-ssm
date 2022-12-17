package com.tujia.myssm.test;

import java.util.Iterator;
import java.util.Map;
import org.junit.Test;
import com.google.common.collect.Maps;
import com.tujia.myssm.utils.base.JsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2022/08/24 17:11
 */
public class testHashMap {

    @Test
    public void test() {
        Map<String, String> map = Maps.newHashMap();
        map.put("1", "1");
        map.put("2", "2");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String nextValue = next.getValue();
            if (nextValue.equals("1")) {
                map.remove(next.getKey());
            }
        }
    }

    @Test
    public void testB() {
        Map<String, String> map = Maps.newHashMap();
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String nextValue = next.getValue();
            if (nextValue.equals("2")) {
                iterator.remove();
            }
        }
        System.out.println("JsonUtils.tryToJson(map) = " + JsonUtils.tryToJson(map));
    }
}
