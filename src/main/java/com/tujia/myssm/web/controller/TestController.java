package com.tujia.myssm.web.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.api.model.OpLog;
import com.tujia.myssm.api.model.OpLogDetail;
import com.tujia.myssm.api.model.excel.SimpleExcelModel;
import com.tujia.myssm.api.model.excel.UnitIdsExcelDownload;
import com.tujia.myssm.common.date.DateTimeRange;
import com.tujia.myssm.service.impl.RedisUtilServiceImpl;
import com.tujia.myssm.utils.base.Joiners;
import com.tujia.myssm.utils.base.JsonUtils;
import com.tujia.myssm.web.controller.biz.TestTx;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/08/03 16:24
 */
@RestController
@RequestMapping("/test/")
@Slf4j
public class TestController extends BaseController {

    @Resource
    private RedisUtilServiceImpl redisUtilServiceImpl;
    @Resource
    private RestTemplate restTemplate;

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

    @GetMapping("test/redis/get")
    public String testRedisGet() {
        //保存数据
        redisUtilServiceImpl.set("name", "imooc");

        String val = redisUtilServiceImpl.get("name");
        log.info("[TestController.testRedisGet] res：{}", JsonUtils.tryToJson(val));
        return val;
    }

    @GetMapping("test/restTemplate")
    public String testRestTemplate() {
        String response = restTemplate.getForObject("https://www.baidu.com", String.class);

        log.info("[TestController.testRedisGet] res：{}", JsonUtils.tryToJson(response));
        return response;
    }

    @GetMapping("test/testGenerateAliasConfig")
    public String testGenerateAliasConfig(@RequestParam("count") int count, @RequestParam("aliasPrefix") String aliasPrefix,
                                          @RequestParam("hostNamePrefix") String hostNamePrefix,
                                          @RequestParam("hostNameSuffix") String hostNameSuffix) {
        Preconditions.checkArgument(count > 0, "count 应该大于0");
        Preconditions.checkArgument(StringUtils.isNotBlank(aliasPrefix), "aliasPrefix 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(hostNamePrefix), "hostNamePrefix 不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(hostNameSuffix), "hostNameSuffix 不能为空");
        final String host = "Host ";
        final String hostName = "Hostname ";
        final String user = "User songlinl";
        StringBuffer result = new StringBuffer();
        for (int i = 0; i <= count; i++) {
            String stringBuffer = host + aliasPrefix + i + "\n" + hostName + hostNamePrefix + i + hostNameSuffix + "\n" + user + "\n\n";
            System.out.println(stringBuffer);
            result.append(stringBuffer);
        }
        return result.append("\n").toString();
    }

    @GetMapping("test/queryHostList")
    public String testQueryHostList(@RequestParam("appCode") String appCode) {
        return null;
    }

    @PostMapping(value = "/uploadFile", produces = "application/json")
    public APIResponse<String> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {

        try {
            if (Objects.isNull(file)) {
                return APIResponse.returnFail("上传文件不能为空");
            }
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            log.info("上传的文件名为:{}", originalFilename);

            List<SimpleExcelModel> excelList = Lists.newArrayList();
            EasyExcel.read(file.getInputStream(), SimpleExcelModel.class, new AnalysisEventListener<SimpleExcelModel>() {

                @Override
                public void invoke(SimpleExcelModel data, AnalysisContext context) {
                    if (data != null) {
                        excelList.add(data);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {

                }
            }).sheet().doRead();
            return APIResponse.returnFail(Joiners.COMMA_JOINER.skipNulls().join(excelList));
        } catch (Exception e) {
            log.error("error:", e);
            return APIResponse.returnFail(e.getMessage());
        }

    }

    @PostMapping(value = "/uploadFile.htm", produces = "application/json")
    public APIResponse<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        try {
            if (Objects.isNull(file)) {
                return APIResponse.returnFail("上传文件不能为空");
            }
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            log.info("上传的文件名为:{}", originalFilename);

            List<SimpleExcelModel> excelList = Lists.newArrayList();
            EasyExcel.read(file.getInputStream(), SimpleExcelModel.class, new AnalysisEventListener<SimpleExcelModel>() {

                @Override
                public void invoke(SimpleExcelModel data, AnalysisContext context) {
                    if (data != null) {
                        excelList.add(data);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {

                }
            }).sheet().doRead();
            return APIResponse.returnFail(Joiners.COMMA_JOINER.skipNulls().join(excelList));
        } catch (Exception e) {
            log.error("error:", e);
            return APIResponse.returnFail(e.getMessage());
        }

    }

    @Resource
    private TestTx testTx;

    @GetMapping(value = "testTx")
    @ResponseBody
    public void testTx() {
        List<OpLog> opLogs = Lists.newArrayList();
        opLogs.add(OpLog.builder().operator("Deadlock").detail(OpLogDetail.builder().summary("1").build()).build());
        opLogs.add(OpLog.builder().operator("Deadlock1").detail(OpLogDetail.builder().summary("11").build()).build());
        opLogs.add(OpLog.builder().operator("Deadlock2").detail(OpLogDetail.builder().summary("12").build()).build());
        opLogs.add(OpLog.builder().operator("Deadlock3").detail(OpLogDetail.builder().summary("13").build()).build());

        testTx.batchUpdate(opLogs);

    }

}
