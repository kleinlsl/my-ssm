package com.tujia.myssm.api.model.json.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2022/08/22 15:49
 */
@Getter
@Setter
@JsonTypeName(value = "Elephant")
public class Elephant extends Animal {

    private String elephant;

}
