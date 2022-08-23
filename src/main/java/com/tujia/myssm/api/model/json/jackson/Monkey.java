package com.tujia.myssm.api.model.json.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2022/08/22 15:50
 */
@Getter
@Setter
@JsonTypeName(value = "Monkey")
public class Monkey extends Animal {

    private String monkey;

    public Monkey(String monkey) {
        this.monkey = monkey;
    }
}
