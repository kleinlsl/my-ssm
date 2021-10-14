package com.tujia.myssm.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 红包到期提醒记录
 * @author: songlinl
 * @create: 2021/10/13 09:47
 */
@Getter
@Setter
@ToString
public class PromoDueReminder implements Serializable {
    private static final long serialVersionUID = 2766237106439684544L;

    /**
     * 主键id
     */
    private long id;

    /**
     * 会员id
     */
    private long memberId;
    /**
     * 券code
     */
    private String promoCode;
    /**
     * 券到期时间
     */
    private LocalDateTime toTime;
    /**
     * 提醒次数
     */
    private int number;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;
}
