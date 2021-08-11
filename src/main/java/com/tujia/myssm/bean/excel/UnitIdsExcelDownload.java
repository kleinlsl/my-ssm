package com.tujia.myssm.bean.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: songlinl
 * @create: 2021/08/03 11:07
 */
@Data
public class UnitIdsExcelDownload implements Serializable {

    @ExcelProperty(value = "房屋id",index = 0)
    private String unitId;
}
