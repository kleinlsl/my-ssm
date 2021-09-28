package com.tujia.myssm.web.controller;

import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.myssm.api.model.excel.UnitIdsExcelDownload;
import com.tujia.myssm.common.utils.JsonUtils;
import com.tujia.myssm.common.utils.date.DateTimeRange;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/08/03 16:24
 */
@RestController
@RequestMapping("/test/")
@Slf4j
public class TestController extends BaseController {

    @GetMapping("test")
    public UnitIdsExcelDownload test() {
        UnitIdsExcelDownload unitIdsExcelDownload = new UnitIdsExcelDownload();
        unitIdsExcelDownload.setUnitId("12");
        return unitIdsExcelDownload;
    }

    @GetMapping("test/dateTimeRange0")
    public DateTimeRange testDateTimeRange0() {
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setStart(LocalDateTime.now());
        dateTimeRange.setEnd(LocalDateTime.now());
        dateTimeRange.setCurrentTime(new Date());
        return dateTimeRange;
    }

    @PostMapping("test/dateTimeRange1")
    public DateTimeRange testDateTimeRange1(@RequestBody DateTimeRange dateTimeRange) {
        dateTimeRange.setCurrentTime(new Date());
        log.info("[TestController.testDateTimeRange1] 请求参数：{}", JsonUtils.tryToJson(dateTimeRange));
        return dateTimeRange;
    }
}
