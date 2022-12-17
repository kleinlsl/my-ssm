package com.tujia.myssm.api.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class SimpleExcelModel {
    @IndexOrder()
    @ExcelProperty(index = 0)
    private String first;
    @ExcelProperty(index = 0)
    @IndexOrder(index = 1)
    private String second;
    @ExcelProperty(index = 0)
    @IndexOrder(index = 2)
    private String third;
    @ExcelProperty(index = 0)
    @IndexOrder(index = 3)
    private String fourth;
    @ExcelProperty(index = 0)
    @IndexOrder(index = 4)
    private String fifth;

}
