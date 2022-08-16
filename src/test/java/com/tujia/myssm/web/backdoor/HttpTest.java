package com.tujia.myssm.web.backdoor;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.api.model.SimpleExcel;
import com.tujia.myssm.common.utils.Joiners;
import com.tujia.myssm.common.utils.JsonUtils;
import com.tujia.myssm.http.HttpClientInvoker;
import com.tujia.myssm.utils.SimpleExcelUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/01/25 16:46
 */
@Slf4j
public class HttpTest {

    private static final HttpClientInvoker tClient = new HttpClientInvoker(100, 100);

    private static APIResponse sendReq(String reqUrl) throws IOException {
        try {
            String json = tClient.httpGet(reqUrl);
            return JsonUtils.readValue(json, APIResponse.class);
        } catch (Exception e) {
            log.error("is exception", e);
            return APIResponse.returnFail("http is fail");
        }
    }

    @Test
    public void testHttp() throws IOException {
        String url = "http://baidu.com";
        String res = tClient.httpGet(url);
        System.out.println(res);
    }

    @Test
    public void simpleRead() {
        String fileName = "C:\\Users\\songlinl\\Downloads\\20220729_100077_5459dcf975a743f9b95a0d290efaff35.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        System.out.println(JsonUtils.tryToJson(excelList));
    }

    @Test
    public void testPrice() throws IOException {
        String url = "http://l-hds-task1.rd.tj.cna:8080/schedule/elonghotel/getV2Mapping.htm?vhotelIds=";
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\001.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<String> unitIdList = excelList.stream().map(SimpleExcel::getFirst).collect(Collectors.toList());
        //        List<String> unitIdList = Lists.newArrayList("17556328", "17369605");
        List<List<String>> unitIdsList = Lists.partition(unitIdList, 100);
        System.out.println("total = " + unitIdList.size());
        final int pageSize = unitIdsList.size();
        int index = 0;
        for (List<String> list : unitIdsList) {
            System.out.println("pageSize = " + pageSize + "  ->  index = " + index);
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (Exception e) {
                log.error("sleep is error", e);
            }
            String param = Joiners.UNDERLINE_JOINER.skipNulls().join(list);
            String reqUrl = url + param;
            APIResponse response = sendReq(reqUrl);
            if (response == null || !response.isRet()) {
                System.out.println("reqUrl = " + reqUrl);
                System.out.println("response = " + JsonUtils.tryToJson(response));
            }
            index++;
        }
        System.out.println("end -----");

    }

    @Test
    public void pushPrice() throws IOException {
        String url = "http://l-hds-task1.rd.tj.cna:8080/schedule/elonghotel/pushPrice.htm?unitId=";
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\pushPrice.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<String> datas = excelList.stream().map(SimpleExcel::getFirst).collect(Collectors.toList());
        System.out.println("total = " + datas.size());
        final int pageSize = datas.size();
        int index = 0;
        for (String val : datas) {
            System.out.println("pageSize = " + pageSize + "  ->  index = " + index);
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (Exception e) {
                log.error("sleep is error", e);
            }
            if (StringUtils.isBlank(val)) {
                continue;
            }
            String reqUrl = url + val.trim();
            APIResponse response = sendReq(reqUrl);
            if (response == null || !response.isRet()) {
                System.out.println("reqUrl = " + reqUrl);
                System.out.println("response = " + JsonUtils.tryToJson(response));
            }
            index++;
        }
        System.out.println("end -----");

    }

    @Test
    public void testPromo() {
        String temp = "alter table PromoUseLog_%s modify column serial_no varchar(256) NOT NULL DEFAULT '' COMMENT '卡券包使用流水号  防止重复提交';";
        for (int i = 0; i < 128; i++) {
            System.out.println(String.format(temp, i));
        }
    }

    @Test
    public void hex() {
        Long max = Long.MAX_VALUE;
        System.out.println(max);
        System.out.println(Long.toHexString(max));
    }

    @Test
    public void testExcel() throws FileNotFoundException {
        String url = "https://pic301.beta.tujia.com/direct/mnbp/redpacketExcelFiles/20220729_100077_75c360bd5aa64cf3abf5a23fdc4823f1.xlsx";
        RestTemplate restTemplate = new RestTemplate();
        byte[] download = restTemplate.getForObject(url, byte[].class);
        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(download));
        //        String fileName = "C:\\Users\\songlinl\\Downloads\\20220729_100077_75c360bd5aa64cf3abf5a23fdc4823f1.xlsx";
        //        inputStream = new BufferedInputStream(new FileInputStream(fileName));
        EasyExcel.read(inputStream, SimpleExcel.class, new AnalysisEventListener<SimpleExcel>() {

            @Override
            public void invoke(SimpleExcel data, AnalysisContext context) {
                System.out.println("data = " + JsonUtils.tryToJson(data));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }
        }).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
        System.out.println("url = " + url);
    }
}
