package com.tujia.myssm.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtherPrizeVo {

    @ApiModelProperty(value = "头像链接", dataType = "String")
    private String headUrl;
    @ApiModelProperty(value = "中奖人", dataType = "String")
    private String luckPeople;
    @ApiModelProperty(value = "奖品名称", dataType = "String")
    private String prizeName;
}
