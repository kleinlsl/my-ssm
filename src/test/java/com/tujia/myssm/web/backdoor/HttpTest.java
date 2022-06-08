package com.tujia.myssm.web.backdoor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.tujia.framework.api.APIResponse;
import com.tujia.framework.diff.danielbechler.util.Collections;
import com.tujia.myssm.api.model.SimpleExcel;
import com.tujia.myssm.common.utils.Joiners;
import com.tujia.myssm.common.utils.JsonUtils;
import com.tujia.myssm.http.HttpClientInvoker;
import com.tujia.myssm.utils.SimpleExcelUtils;
import com.tujia.tns.baseinfo.search.api.vo.mix.HousePrice;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/01/25 16:46
 */
@Slf4j
public class HttpTest {

    private static final HttpClientInvoker tClient = new HttpClientInvoker(100, 100);

    private static List<HousePrice> sendReq(String reqUrl) throws IOException {
        try {
            String json = tClient.httpGet(reqUrl);
            APIResponse<List<HousePrice>> response = JsonUtils.readValue(json, new TypeReference<APIResponse<List<HousePrice>>>() {});
            if (response == null || !response.isRet() || Collections.isEmpty(response.getData())) {
                log.warn("is error");
                return null;
            }
            return response.getData();
        } catch (Exception e) {
            log.error("is exception", e);
        }
        return null;
    }

    @Test
    public void testHttp() throws IOException {
        String url = "http://baidu.com";
        String res = tClient.httpGet(url);
        System.out.println(res);
    }

    @Test
    public void simpleRead() {
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\openprice_provider_1_9_cna.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        System.out.println(JsonUtils.tryToJson(excelList));
    }

    @Test
    public void testPrice() throws IOException {
        String url = "http://l-openprice-provider1.rd.tj.cna:8080/provider/thirdpart/getHouseSearchPriceList.htm?unitIds=";
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\openprice_provider_1_9_cna.xlsx";
        List<SimpleExcel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<String> unitIdList = excelList.stream().map(SimpleExcel::getFirst).collect(Collectors.toList());

        List<List<String>> unitIdsList = Lists.partition(unitIdList, 1000);
        List<Long> allResult = Lists.newArrayList();
        for (List<String> list : unitIdsList) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (Exception e) {
                log.error("sleep is error", e);
            }
            String param = Joiners.COMMA_JOINER.skipNulls().join(list);
            String reqUrl = url + param;
            List<HousePrice> housePrices = sendReq(reqUrl);
            List<Long> result = housePrices.stream().filter(h -> h.getChannel() == 303).map(HousePrice::getHouseId).collect(Collectors.toList());
            allResult.addAll(result);
        }
        System.out.println(fileName + "  -->  source:" + excelList.size() + "  ;  allResult:" + allResult.size() + "  -->  allResult = " +
                                   Joiners.COMMA_JOINER.skipNulls().join(allResult));
    }
}
