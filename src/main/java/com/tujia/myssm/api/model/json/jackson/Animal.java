package com.tujia.myssm.api.model.json.jackson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * https://www.jianshu.com/p/a21f1633d79c
 * @author: songlinl
 * @create: 2022/08/22 15:48
 */
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Monkey.class), @JsonSubTypes.Type(value = Elephant.class) })
public abstract class Animal {
    private String name;

}
