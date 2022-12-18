package com.tujia.myssm.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tujia.myssm.common.date.model.LocalDateRangeSet;
import com.tujia.myssm.common.date.serializer.LocalDateRangeSetSerializer;
import com.tujia.myssm.core.diff.annotation.Diff;
import com.tujia.myssm.core.diff.annotation.DiffIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 18:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HdsSaleBlacklist implements Serializable {

    private static final long serialVersionUID = -1513487314767475090L;

    @DiffIgnore
    private Long id;

    private Long unitId;

    @Diff("信用免押金")
    private String saleChannel;

    @JsonSerialize(using = LocalDateRangeSetSerializer.Serializer.class)
    @JsonDeserialize(using = LocalDateRangeSetSerializer.Deserializer.class)
    private LocalDateRangeSet dateRanges;

    private int status;

    private int dataEntityStatus;

    @Diff("信用免押金")
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
