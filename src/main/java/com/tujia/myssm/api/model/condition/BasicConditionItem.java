package com.tujia.myssm.api.model.condition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tujia.myssm.utils.base.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * 基础组件 - 对应前端组件模型
 *
 * @author: songlinl
 * @create: 2024/5/24 13:52
 */
@Getter
@Setter
public class BasicConditionItem implements Serializable {

    private static final long serialVersionUID = 1806507365433060478L;

    private Integer id;

    /**
     * 显示名称
     */
    private String name;

    private String type;

    /**
     * 是否支持过滤
     */
    private Boolean filterable;

    /**
     * 输入类型最大值
     */
    private BigDecimal max;

    /**
     * 输入类型最小值
     */
    private BigDecimal min;

    /**
     * 是否支持多选
     */
    private Boolean multiple;

    /**
     * 单选项 selected value
     */
    @JsonProperty("value")
    private String selectedValue;

    /**
     * 多选项 selected value;
     */
    @JsonProperty("values")
    private List<String> selectedValues;

    /**
     * 可选项
     */
    private List<ConditionItem> options;

    public boolean selected() {
        return false;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String json = scanner.nextLine();

        GetConditionsVo vo = JsonUtils.readValue(json, GetConditionsVo.class);
        System.out.println(vo);
    }

}
