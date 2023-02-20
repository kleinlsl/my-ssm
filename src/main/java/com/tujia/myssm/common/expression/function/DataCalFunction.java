package com.tujia.myssm.common.expression.function;

import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;

/**
 *
 * @author: songlinl
 * @create: 2023/01/04 19:04
 */
public interface DataCalFunction {

    /**
     * 计算表达式
     * @param env
     * @param rule
     * @return
     */
    boolean doCal(AviatorInput env, ExpressionRule rule);

}
