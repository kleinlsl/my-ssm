package com.tujia.myssm.api.model.condition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: songlinl
 * @create: 2024/5/21 20:00
 */
@Getter
@Setter
@NoArgsConstructor
public class GetConditionsVo implements Serializable {
    private static final long serialVersionUID = -8021496964596387095L;

    /**
     * 基础条件 - 上方输入框部分
     */
    private List<BasicConditionItem> basicConditions;

    /**
     * 自定义条件 - 中间动态部分
     */
    private List<BasicConditionItem> customConditions;

    /**
     * 排序条件
     */
    private BasicConditionItem sortConditions;

}
