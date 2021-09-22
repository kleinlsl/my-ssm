package com.tujia.myssm.api.model.excel;

/**
 * @author: songlinl
 * @create: 2021/08/03 16:49
 */

import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class UnitIdsExcel extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = 8919456316743595940L;

    @ExcelProperty(index = 0)
    private String unitId;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return "UnitIdsExcel{" +
                "unitId='" + unitId + '\'' +
                "} " + super.toString();
    }
}