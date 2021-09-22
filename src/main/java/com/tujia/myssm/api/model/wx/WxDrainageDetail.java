package com.tujia.myssm.api.model.wx;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: songlinl
 * @create: 2021/08/31 18:31
 */
@Getter
@Setter
@ToString
public class WxDrainageDetail implements Serializable {
    private static final long serialVersionUID = 333971595908143713L;

    /**
     * 主键id
     */
    private long id;

    /**
     * 途家会员id
     */
    private long memberId;

    /**
     * 卡券包编号
     */
    private String promoCode;

    /**
     * 红包id
     */
    private int activityChannelId;

    /**
     * 活动配置code
     */
    private String activityCode;

    /**
     * 领取状态：1成功，2失败
     */
    private int receive;

    /**
     * 有效期开始日期
     */
    private Date fromTime;

    /**
     * 有效期结束日期
     */
    private Date toTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

}
