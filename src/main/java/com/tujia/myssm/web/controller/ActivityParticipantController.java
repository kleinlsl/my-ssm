package com.tujia.myssm.web.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.tujia.framework.api.APIResponse;
import static com.tujia.framework.cache.lruk.lru.LocalCache.logger;
import com.tujia.framework.utility.StringUtils;
import com.tujia.myssm.api.model.ActivityParticipant;
import com.tujia.myssm.api.model.excel.UnitIdsExcel;
import com.tujia.myssm.api.model.excel.UnitIdsExcelDownload;
import com.tujia.myssm.service.ActivityParticipantService;

/**
 * @author: songlinl
 * @create: 2021/08/03 11:15
 */
@Controller
@RequestMapping("/tpromo/edit/activity/config")
public class ActivityParticipantController {
    @Resource
    private ActivityParticipantService activityParticipantService;


    @ResponseBody
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void download(HttpServletResponse response) {
        try {
            List<UnitIdsExcelDownload> data = data();
            System.out.println(data);

            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("demo.xlsx", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            WriteSheet writeSheet = EasyExcel.writerSheet("密码红包").build();
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UnitIdsExcelDownload.class).build();

            excelWriter.write(data, writeSheet);
            excelWriter.finish();

            System.out.println(response.getOutputStream());
//            return APIResponse.returnSuccess("success");
        } catch (Exception e) {
//            return APIResponse.returnFail("fail");
        }

    }

    /**
     * 红包定向房屋下载
     *
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/downActivityParticipant.htm", method = RequestMethod.GET)
    public APIResponse downActivityParticipant(@RequestParam("activityCode") String activityCode, HttpServletResponse response) {
        try {
            if (StringUtils.isBlank(activityCode)) {
                return APIResponse.returnFail("活动code不可为空");
            }
            ActivityParticipant activityParticipant = activityParticipantService.queryMaxVersionByActivityCode(activityCode);
            if (activityParticipant == null) {
                return APIResponse.returnFail("未指定房屋");
            }
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(activityParticipant.getActivityCode() + "_" + activityParticipant.getVersion() + ".xlsx", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            WriteSheet writeSheet = EasyExcel.writerSheet("房屋").build();
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UnitIdsExcel.class).build();

            String unitIds = "";
            if (activityParticipant.getData() != null) {
                unitIds = new String(activityParticipant.getData());
            } else {
                unitIds = activityParticipant.getUnitIds();
            }

            List<UnitIdsExcel> data = Lists.newArrayList(Splitter.on(",").limit(1 * 1024 * 1024 - 1).split(unitIds))
                    .parallelStream().map(aLong -> {
                        UnitIdsExcel excel = new UnitIdsExcel();
                        excel.setUnitId(String.valueOf(aLong));
                        return excel;
                    }).collect(Collectors.toList());
            excelWriter.write(data, writeSheet);
            excelWriter.finish();
            return APIResponse.returnSuccess();
        } catch (Exception e) {
            logger.info("[downActivityParticipant.htm ] 异常, req:{}", activityCode, e);
            return APIResponse.returnFail(e.getMessage());
        }
    }

    private List<UnitIdsExcelDownload> data() {
        List<UnitIdsExcelDownload> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            UnitIdsExcelDownload unitIdsExcelDownload = new UnitIdsExcelDownload();
//            unitIdsExcelDownload.setUnitId(String.valueOf(i));
//            list.add(unitIdsExcelDownload);
//        }
        return list;
    }
}
