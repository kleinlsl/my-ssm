package com.tujia.myssm.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tujia.myssm.api.enums.EnumOpType;
import com.tujia.myssm.common.utils.serializer.LocalDateTimeDeserializer;
import com.tujia.myssm.common.utils.serializer.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2021/11/03 20:43
 */
@Getter
@Setter
public class OpLog implements Serializable {

    private static final long serialVersionUID = 1667438215591533610L;

    /**
     * id
     */
    private long id;

    /**
     * 操作详情
     */
    private OpLogDetail detail;

    /**
     * 类型
     * @see EnumOpType
     */
    private int type;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
}
