package com.tujia.myssm.api.model.excel;

import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author: songlinl
 * @create: 2021/08/03 11:07
 */
@Data
public class UnitIdsExcelDownload implements Serializable {

    @ExcelProperty(value = "房屋id",index = 0)
    private String unitId;
}
