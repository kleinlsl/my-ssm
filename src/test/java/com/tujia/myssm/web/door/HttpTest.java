package com.tujia.myssm.web.door;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.tujia.myssm.utils.base.Splitters;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.api.model.excel.SimpleExcelModel;
import com.tujia.myssm.core.http.HttpClientInvoker;
import com.tujia.myssm.utils.base.Joiners;
import com.tujia.myssm.utils.base.JsonUtils;
import com.tujia.myssm.utils.excel.SimpleExcelUtils;
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
    public void  test() throws IOException {
        String url = "http://l-hds-web3.rd.tj.cna:8080//api/qunar/getpriceinfo?hotelId=16199179&fromDate=2023-06-09&toDate=2023-06-10&name=songlinl";
        String res = tClient.httpGet(url,1,1000);
        System.out.println("res = " + res);
    }

    @Test
    public void simpleRead() {
        String fileName = "C:\\Users\\songlinl\\Downloads\\20220729_100077_5459dcf975a743f9b95a0d290efaff35.xlsx";
        List<SimpleExcelModel> excelList = SimpleExcelUtils.simpleRead(fileName);
        System.out.println(JsonUtils.tryToJson(excelList));
    }

    @Test
    public void testGetMapping() throws IOException {
        String url = "http://l-hds-task1.rd.tj.cna:8080/schedule/elonghotel/getV2Mapping.htm?vhotelIds=";
        String fileName = "C:\\Users\\songlinl\\Desktop\\新建文件夹\\2022-11-01\\getMapping.xlsx";
        List<SimpleExcelModel> excelList = SimpleExcelUtils.simpleRead(fileName);
        Set<String> excludeList = excelList.stream().map(SimpleExcelModel::getFirst).filter(v -> !NumberUtils.isNumber(v)).collect(Collectors.toSet());
        Set<String> unitIdList = excelList.stream().map(SimpleExcelModel::getFirst).filter(NumberUtils::isNumber).collect(Collectors.toSet());
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
        List<SimpleExcelModel> excelList = SimpleExcelUtils.simpleRead(fileName);
        List<String> datas = excelList.stream().filter(s -> NumberUtils.isNumber(s.getSecond())).map(SimpleExcelModel::getFirst).collect(
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
    public void testOpenPrice() throws IOException {
        String url = "http://l-openprice-task7.rd.tj.cna:8080/provider/thirdpart/getUnitProductAndRuleList.htm?unitIds=";
        String unitIdStrs = "1002509, 36083237, 50287348, 42945155, 7435388, 50287345, 56914804, 62477224, 50287342, 17801498, 41381474, 36083251, 48488182, 52596535, 36095536, 62477218, 62477221, 17801491, 62477209, 62840734, 58174291, 62840731, 62477212, 62477215, 62840728, 46132888, 59788216, 62477203, 31548875, 7280709, 37531159, 50043607, 62477206, 40417882, 33823280, 33823294, 17282364, 37648899, 423995, 42050240, 36085351, 424004, 36086373, 56058643, 2839662, 56914723, 22937874, 2839655, 37161594, 62349304, 7645220, 7280702, 61735933, 22937867, 52606837, 62478307, 36085372, 12077283, 57104185, 10016966, 54589216, 6575109, 57598735, 7645206, 20078934, 36083265, 7645213, 52191088, 36082250, 58174234, 29574574, 36083272, 36083286, 58174213, 22552874, 36084308, 54686518, 7645185, 29574581, 54020929, 7645199, 36083293, 36082264, 57892849, 31671655, 59069194, 43602441, 37656235, 14465088, 8332514, 52629436, 37531292, 12467225, 37656214, 59069230, 24105411, 16949682, 7673029, 7199964, 37656207, 36082327, 31671634, 51341245, 58861360, 7673030, 41385667, 6343892, 24105432, 46273141, 7199957, 31671641, 6343899, 62302984, 24105425, 62479111, 41480902, 15252567, 7505101, 59069242, 37655290, 25378123, 41864887, 58349491, 36088557, 37654261, 59069257, 36082411, 37655283, 7505059, 25378130, 61157236, 24105404, 62477152, 24105397, 37655269, 15106106, 38793875, 56690620, 59069275, 62477149, 25378151, 37655255, 453871, 41489043, 5080231, 29364525, 7057563, 42686078, 453872, 31672607, 57121690, 59753329, 7057556, 62476987, 36088613, 56595034, 62476990, 36088620, 51340804, 62476981, 54358573, 62476969, 12805591, 62476975, 42139546, 7332215, 62476960, 55337554, 43308980, 49716181, 15018438, 62476957, 36089600, 62476945, 61115020, 51548731, 59016886, 57149020, 59016892, 43308973, 59016895, 39443300, 22552678, 37655297, 49716151, 62477050, 17799237, 36071274, 43563990, 55382530, 14344597, 53473888, 53142139, 62477032, 36086653, 62477026, 36084602, 55822861, 36071288, 62477017, 54276697, 59069155, 62477023, 36088641, 15420812, 36081487, 62477011, 62477014, 36088662, 53886532, 9892324, 62477002, 62477005, 49716097, 57261598, 54569530, 29364413, 59069179, 60056086, 36087717, 58174207, 45528892, 62476855, 36085687, 42617625, 13211997, 57588472, 36085680, 36085694, 57257719, 36085638, 36086660, 59019808, 36084609, 41189331, 62476816, 42617632, 36086667, 9342243, 62476822, 36084630, 36086674, 60028453, 57667288, 41189324, 62476801, 36090783, 59019838, 62477827, 36084637, 62476807, 36082663, 42349378, 51238603, 51238597, 539074, 50976451, 57855676, 342475, 51238594, 42349385, 48420661, 59019868, 42869590, 474590, 39247755, 1114575, 4169181, 7332236, 42830698, 52727503, 1114593, 59019898, 54050503, 22815577, 21286721, 37657656, 6789743, 48448744, 54211894, 48823525, 53871928, 42737794, 37656620, 17799965, 36091952, 36087871, 37656606, 42632367, 17799972, 3522073, 56239477, 39247993, 2875908, 62477707, 62708098, 54349183, 49188010, 36081781, 37657705, 36092029, 62700001, 37658720, 558699, 37657691, 36091980, 37657684, 53694808, 55963966, 33822825, 17801057, 43314426, 37657677, 36083797, 33822832, 37657670, 36082782, 57133342, 36083804, 48898198, 56453410, 94850, 39740632, 53553592, 41105660, 36087990, 57293305, 48676981, 40417532, 54577624, 36087997, 7720686, 52025785, 42400807, 42693673, 52025788, 52025791, 51665341, 50951614, 45059095, 62477569, 37657733, 45059092, 62477689, 62477692, 40309921, 62477695, 36088046, 62477680, 39248028, 50924995, 62477683, 40988858, 53872120, 62477686, 51845575, 39248007, 30247708, 34076883, 62477677, 52025794, 52025797, 54245851, 62477656, 62477659, 14386751, 51168745, 54245854, 62477662, 42651750, 36088018, 62477632, 62477635, 59111806, 47572009, 30247729, 36088025, 62477638, 62477497, 46097854, 29915852, 36086051, 62477500, 31673062, 29394632, 62477488, 36086058, 62477494, 9401246, 55448668, 62477485, 62478508, 36090160, 44793277, 3324708, 60793993, 36086065, 31673076, 31673083, 23708273, 62821543, 62477479, 36086072, 54587488, 36086023, 54236185, 56987713, 42727854, 13391841, 31549134, 62477449, 36085015, 29394674, 36086037, 59712700, 62477440, 56415334, 62477443, 12702719, 62477446, 62477560, 42650049, 36083048, 51521602, 36083062, 37653869, 5996300, 36089201, 37653862, 62477539, 36083069, 62478565, 51160144, 44925427, 44793289, 62477533, 54655012, 58901737, 44793286, 62477524, 40417560, 62477515, 29394611, 37653834, 62478541, 33855858, 62477518, 62478529, 44793298, 29394618, 56477734, 62477506, 36092253, 36090202, 52059238, 62478535, 36087206, 53484724, 33855872, 36086177, 7645178, 57245944, 55965889, 42651932, 52534462, 41483737, 10224446, 31671872, 40417728, 62384158, 7151552, 18714284, 37657999, 36083090, 52231336, 42651967, 42727742, 59249725, 62781451, 56740054, 62477434, 62477437, 62462065, 62477425, 62477428, 42727749, 58861654, 62477422, 40417714, 14370567, 42727791, 59708518, 62477395, 62478421, 38566305, 59629693, 11696966, 49726723, 36090335, 48290077";

        List<String> datas = Splitters.COMMA_SPLITTER.splitToList(unitIdStrs);
        System.out.println("total = " + datas.size());
        final int pageSize = datas.size();
        int index = 0;
        long sleep = 500L;
        Map<String, String> errorMap = Maps.newHashMap();
        Map<String, Object> succMap = Maps.newHashMap();
        for (String val : datas) {
            log.info("pageSize = " + pageSize + "  ->  index = " + index);
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
            } else {
                succMap.put(val.trim(), JsonUtils.tryToJson(response).length());
            }
            index++;
        }

        log.info("err:{}", JsonUtils.tryToJson(errorMap));
        log.info("succ:{}", JsonUtils.tryToJson(succMap));
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
