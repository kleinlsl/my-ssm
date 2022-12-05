package com.tujia.myssm.api.model.json.jackson;

import com.tujia.myssm.common.utils.JsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2022/08/22 15:50
 */
public class TestMain {

    public static void main(String[] args) {

        Elephant elephant = new Elephant();
        elephant.setName("孤单的大象");
        String elephantJson = JsonUtils.toJson(elephant);
        System.out.println(elephantJson);

        Elephant anotherElephant = new Elephant();
        anotherElephant.setName("另一头孤单的大象");
        //        anotherElephant.setType("大象");
        String anotherElephantJson = JsonUtils.toJson(anotherElephant);
        System.out.println(anotherElephantJson);

        Animal animal = JsonUtils.readValue(anotherElephantJson, Animal.class);
        System.out.println(animal instanceof Elephant);
    }
}
