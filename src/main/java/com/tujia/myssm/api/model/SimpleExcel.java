package com.tujia.myssm.api.model;

import com.tujia.framework.excel.IndexOrder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2022/06/07 20:04
 */
@Getter
@Setter
public class SimpleExcel {
    @IndexOrder(index = 0)
    private String first;
    @IndexOrder(index = 1)
    private String second;
    @IndexOrder(index = 2)
    private String third;
    @IndexOrder(index = 3)
    private String fourth;
    @IndexOrder(index = 4)
    private String fifth;

}
