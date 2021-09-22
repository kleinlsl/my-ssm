package com.tujia.myssm.api.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author: songlinl
 * @create: 2021/08/06 14:34
 */
@Data
public class CityCashBack implements Serializable {

    private static final long serialVersionUID = -5800081678632988597L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 途家城市id
     */
    private Long cityId;

    /**
     * 返现金额
     */
    private Integer amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;
}
