package com.tujia.myssm.api.model.proto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.dyuproject.protostuff.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * @author: songlinl
 * @create: 2022/08/17 11:08
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Tag(value = 2)
    private String name;
    @Tag(value = 1)
    private Long id;
    @Tag(value = 3)
    private BigDecimal amount;
    @Tag(value = 4)
    private List<String> pages;

    @Tag(value = 5)
    private int type;

    @Tag(value = 6)
    private Page page;

    @Tag(value = 7)
    private PayType payType;

    @Tag(value = 8)
    private Map<String, String> sm;

}
