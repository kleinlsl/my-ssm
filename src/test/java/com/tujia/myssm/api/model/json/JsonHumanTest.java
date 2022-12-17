package com.tujia.myssm.api.model.json;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.tujia.myssm.utils.base.JsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2021/12/27 17:08
 */
public class JsonHumanTest {

    public static void main(String[] args) {
        Map<Long, String> map = Maps.newHashMap();
        map.put(1L, "llll");
        map.put(2L, "2222");
        String mapdata = JsonUtils.toJson(map);
        System.out.println("JsonUtils.toJson(map) = " + JsonUtils.toJson(map));
        map = JsonUtils.readValue(mapdata, new TypeReference<Map<Long, String>>() {});
        System.out.println("mapdata = " + map);
        System.out.println("new HashMap<>().containsKey(null) = " + new HashMap<>().containsKey(null));
    }

    @Test
    public void test() {
        JsonHuman.Man man = new JsonHuman.Man();
        man.setManField("男人");
        man.setDistrict("北京");

        String json = JsonUtils.toJson(man);
        System.out.println("man = " + json);
        JsonHuman human = JsonUtils.readValue(json, JsonHuman.class);
        System.out.println("human = " + human);
    }

    @Test
    public void testEXTERNAL_PROPERTY() {
        JsonModel jsonModel = new JsonModel();
        jsonModel.setType("man");
        JsonHuman.Man man = new JsonHuman.Man();
        jsonModel.setJsonHuman(man);
        man.setDistrict("beijing");
        man.setManField("男人");
        String json = JsonUtils.toJson(jsonModel);
        System.out.println("json = " + json);
        JsonModel model = JsonUtils.readValue(json, JsonModel.class);
        System.out.println("model = " + model);
    }
}