package com.tujia.myssm.web.vo;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import com.tujia.myssm.api.model.excel.SimpleExcelModel;
import com.tujia.myssm.utils.base.JsonUtils;
import com.tujia.myssm.utils.excel.SimpleExcelUtils;

/**
 *
 * @author: songlinl
 * @create: 2022/11/15 14:58
 */
public class OtherPrizeVoTest {

    @Test
    public void read() {
        String fileName = "C:\\Users\\songlinl\\Desktop\\抽奖\\轮播图\\转盘氛围条轮播-221117-TClhPT6goZpA.xlsx";
        List<SimpleExcelModel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<OtherPrizeVo> prizeVos = excelList.stream().map(this::convert).collect(Collectors.toList());
        System.out.println(JsonUtils.tryToJson(prizeVos));
    }

    private OtherPrizeVo convert(SimpleExcelModel excel) {
        OtherPrizeVo prizeVo = new OtherPrizeVo();
        prizeVo.setHeadUrl(excel.getFirst());
        prizeVo.setLuckPeople(excel.getSecond());
        prizeVo.setPrizeName(excel.getThird());
        return prizeVo;
    }

}