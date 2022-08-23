package com.tujia.myssm.api.model.json.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author: songlinl
 * @create: 2022/08/22 15:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName(value = "Elephant")
public class Elephant extends Animal {

}
