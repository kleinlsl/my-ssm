package com.tujia.myssm.web.backdoor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
            String json = tClient.httpGet(reqUrl, 5000, 5000);
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
    public void testGetMapping() throws IOException {
        String url = "http://l-hds-task1.rd.tj.cna:8080/schedule/elonghotel/getV2Mapping.htm?vhotelIds=";
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\2022-11-01\\getMapping.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        Set<String> excludeList = excelList.stream().map(SimpleExcel::getFirst).filter(v -> !NumberUtils.isNumber(v)).collect(Collectors.toSet());
        Set<String> unitIdList = excelList.stream().map(SimpleExcel::getFirst).filter(NumberUtils::isNumber).collect(Collectors.toSet());
        List<List<String>> unitIdsList = Lists.partition(Lists.newArrayList(unitIdList), 100);
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
        System.out.println("excludeList = " + Joiners.COMMA_JOINER.skipNulls().join(excludeList));

    }

    @Test
    public void pushPrice() throws IOException {
        String url = "http://l-hds-task1.rd.tj.cna:8080/schedule/elonghotel/pushPrice.htm?unitId=";
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\2022-08-19\\pushPrice.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<String> datas = excelList.stream().filter(s -> NumberUtils.isNumber(s.getSecond())).map(SimpleExcel::getFirst).collect(
                Collectors.toList());
        System.out.println("total = " + datas.size());
        final int pageSize = datas.size();
        int index = 0;
        long sleep = 500L;
        Map<String, String> errorMap = Maps.newHashMap();
        for (String val : datas) {
            log.info("pageSize = " + pageSize + "  ->  index = " + index);
            if (index < 35731) {
                index++;
                continue;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(sleep);
            } catch (Exception e) {
                log.error("sleep is error", e);
            }
            if (StringUtils.isBlank(val)) {
                continue;
            }
            String reqUrl = url + val.trim();
            APIResponse response = sendReq(reqUrl);
            if (response == null || !response.isRet()) {
                errorMap.put(val.trim(), response == null ? "response is null" : response.getErrmsg());
            }
            index++;
        }

        log.info("{}", JsonUtils.tryToJson(errorMap));
        System.out.println("end -----");
    }

    @Test
    public void testPromo() {
        String temp = "alter table PromoUseLog_%s modify column serial_no varchar(256) NOT NULL DEFAULT '' COMMENT '卡券包使用流水号  防止重复提交';";
        for (int i = 0; i < 128; i++) {
            System.out.println(String.format(temp, i));
        }
    }


}
