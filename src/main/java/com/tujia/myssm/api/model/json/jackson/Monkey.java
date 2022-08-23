package com.tujia.myssm.api.model.json.jackson;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author: songlinl
 * @create: 2022/08/22 15:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonTypeName(value = "Monkey")
public class Monkey extends Animal {}
