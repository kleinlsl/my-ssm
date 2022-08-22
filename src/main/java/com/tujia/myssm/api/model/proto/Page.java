package com.tujia.myssm.api.model.proto;

import com.dyuproject.protostuff.Tag;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author: songlinl
 * @create: 2022/08/17 15:26
 */
@Data
@Accessors(chain = true)
public class Page {
    @Tag(value = 1)
    private int num;
    @Tag(value = 2)
    private String name;
}
