package com.tujia.myssm.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: songlinl
 * @create: 2021/08/03 11:06
 */
@Data
public class UnitIdsExcelUpload {
    @ExcelProperty(value = "房屋id",index = 0)
    private String unitId;
}
