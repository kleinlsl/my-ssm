package com.tujia.myssm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.myssm.api.model.excel.UnitIdsExcelDownload;

/**
 * @author: songlinl
 * @create: 2021/08/03 16:24
 */
@RestController
@RequestMapping("/tpromo/edit/activity/config")
public class TestController {

    @GetMapping("test")
    public UnitIdsExcelDownload test(){
        UnitIdsExcelDownload unitIdsExcelDownload = new UnitIdsExcelDownload();
        unitIdsExcelDownload.setUnitId("12");
       return unitIdsExcelDownload;
    }
}
