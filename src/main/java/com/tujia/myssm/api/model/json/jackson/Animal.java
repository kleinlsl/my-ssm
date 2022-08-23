package com.tujia.myssm.api.model.json.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 * https://www.jianshu.com/p/a21f1633d79c
 * @author: songlinl
 * @create: 2022/08/22 15:48
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
public abstract class Animal {
    private String name;

    private String type;

}
