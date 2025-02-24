package com.tujia.myssm.api.model.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * condition Item
 *
 * @author: songlinl
 * @create: 2024/5/24 13:37
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionItem implements Serializable {
    private static final long serialVersionUID = -1777851540010488902L;

    private Integer id;

    /**
     * 筛选项名称:
     * eg:出租类型、整租、景观、河景等展示信息
     */
    private String label;

    /**
     * 筛选项 value 码
     */
    private String value;

    /**
     * 赔付比例 会用到 max （允许赔付的最大值）
     */
    private BigDecimal max;

    /**
     * 选择类型 - 0-无 1-单选项 2-复选项 3-开关项
     */
    private int selectedType;

    /**
     * 子元素
     */
    private List<ConditionItem> childList;

    /**
     * 经度 - 地标或城市会有
     * todo 是否必要
     */
    private double longitude = 0;

    /**
     * 纬度 - 地标或城市会有
     * todo 是否必要
     */
    private double latitude = 0;
    /**
     * 拼音 - 地标或城市会有
     * todo 是否必要
     */
    private String pinYin;


}
