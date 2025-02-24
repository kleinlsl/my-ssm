package com.tujia.myssm.web.excel;

import com.tujia.myssm.api.model.excel.SimpleExcelModel;
import com.tujia.myssm.utils.base.JsonUtils;
import com.tujia.myssm.utils.excel.SimpleExcelUtils;
import com.tujia.myssm.web.vo.StaffBenefitsConfig;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: songlinl
 * @create: 2023/7/6 11:41
 */
public class ExcelRead {


    @Test
    public void read() {
        String fileName = "C:\\Users\\songlinl\\Desktop\\员工福利券-配置1.xlsx";
        List<SimpleExcelModel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<StaffBenefitsConfig> prizeVos = excelList.stream().map(this::convert).collect(Collectors.toList());
        System.out.println(JsonUtils.tryToJson(prizeVos));
    }

    private StaffBenefitsConfig convert(SimpleExcelModel excel) {
        StaffBenefitsConfig prizeVo = new StaffBenefitsConfig();
        prizeVo.setActivityName(excel.getFirst().trim());
        prizeVo.setActivityCode(excel.getSecond().trim());
        prizeVo.setTotalCount(NumberUtils.toInt(excel.getThird().trim()));
        prizeVo.setRuleDesc(excel.getFourth());
        return prizeVo;
    }
}
