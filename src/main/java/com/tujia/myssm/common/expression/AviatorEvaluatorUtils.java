package com.tujia.myssm.common.expression;

import java.util.List;
import java.util.Map;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.tujia.myssm.common.expression.builder.ExpressionBuilder;
import com.tujia.myssm.common.expression.function.aviator.DoCalFunction;
import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;

/**
 *
 * @author: songlinl
 * @create: 2023/01/03 11:12
 */
public class AviatorEvaluatorUtils {
    private static final Map<String, Expression> cached = Maps.newConcurrentMap();

    static {
        AviatorEvaluator.addFunction(DoCalFunction.getInstance());
    }

    /**
     * 将表达式交给表达式引擎(Aviator)计算
     * @param expression
     * @param env
     * @return
     */
    public static boolean execute(String expression, Map<String, Object> env) {
        Expression expressionObj = cached.get(expression);
        if (expressionObj == null) {
            expressionObj = AviatorEvaluator.compile(expression, true);
            cached.putIfAbsent(expression, expressionObj);
        }
        return (boolean) expressionObj.execute(env);
    }

    /**
     * 1. 计算每个子表达式的结果,并构建出true、false表达式字符串
     * 2. 将true、false表达式字符串交给表达式引擎计算
     * @param env
     * @param ruleList
     * @return
     */
    public static boolean execute(AviatorInput env, List<ExpressionRule> ruleList) {
        String expression = ExpressionBuilder.build(env, ruleList);
        return execute(expression, env.toAviatorEnvMap());
    }

    /**
     * 1. 构建包含自定义函数(doCal(arg1,arg2,arg3))的表达式字符串
     * 2. 将表达式交给表达式引擎(Aviator)计算
     * @param env
     * @param ruleList
     * @return
     */
    public static boolean executeV2(AviatorInput env, List<ExpressionRule> ruleList) {
        String expression = ExpressionBuilder.buildV2(ruleList);
        return execute(expression, env.toAviatorEnvMap());
    }
}
