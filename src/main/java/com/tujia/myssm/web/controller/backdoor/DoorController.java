package com.tujia.myssm.web.controller.backdoor;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.utils.base.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/09/30 17:04
 */
@Slf4j
@RestController
@RequestMapping("/door")
public class DoorController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/ascendPathChange")
    public APIResponse<String> ascendPathChange(@RequestParam("activityCode") String activityCodes) {
        final String url = "http://pm301.beta.tujia.com/tpromo/backdoor/ascendPathChange?activityCode=";
        try {
            log.info("BackDoorController.syncSalesActivityUnit 入参：{}", activityCodes);
            List<String> activityCodeList = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(
                    activityCodes);
            Preconditions.checkArgument(CollectionUtils.isNotEmpty(activityCodeList), "参数不能为空");
            for (String activityCode : activityCodeList) {
                try {
                    log.info("[BackDoorController] ascendPathChange begin,activityCode:{}", activityCode);
                    APIResponse response = restTemplate.getForObject(url + activityCode, APIResponse.class);
                    log.info("[BackDoorController] ascendPathChange end,activityCode:{},response:{}", activityCode,
                             JsonUtils.tryToJson(response));
                } catch (Exception e) {
                    log.error("[BackDoorController] ascendPathChange error,activityCodes:{}", activityCodeList);
                }
            }
            return APIResponse.returnSuccess();
        } catch (Exception e) {
            log.error("BackDoorController.ascendPathChange 异常", e);
            return APIResponse.returnFail(e.getMessage());
        }
    }
}
