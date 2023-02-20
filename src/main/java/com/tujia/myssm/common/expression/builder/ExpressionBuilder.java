package com.tujia.myssm.common.expression.builder;

import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.googlecode.aviator.AviatorEvaluator;
import com.tujia.myssm.common.expression.enums.Field;
import com.tujia.myssm.common.expression.enums.Operator;
import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;

/**
 *
 * @author: songlinl
 * @create: 2023/01/02 19:05
 */
public class ExpressionBuilder {

    public static String buildV2(List<ExpressionRule> ruleList) {
        StringBuilder expression = new StringBuilder();
        for (ExpressionRule rule : ruleList) {
            if (rule == null) {
                continue;
            }
            Operator logicOperator = Operator.codeOf(rule.getLogicOperator());
            String subExpression = "";
            if (!isSkip(rule)) {
                subExpression = String.format("doCal(%s,%s,'%s')", rule.getField(), rule.getOperator(), rule.getValue());
            }

            StringBuilder expressionBuilder = new StringBuilder();
            if (rule.isLeftBracket()) {
                expressionBuilder.append("(");
            }
            expressionBuilder.append(subExpression);
            if (rule.isRightBracket()) {
                expressionBuilder.append(")");
            }
            if (logicOperator != null) {
                expressionBuilder.append(logicOperator.getCharCode());
            }
            expression.append(expressionBuilder.toString());
        }
        return expression.toString();
    }

    public static String build(AviatorInput env, List<ExpressionRule> ruleList) {
        StringBuilder expression = new StringBuilder();
        for (ExpressionRule rule : ruleList) {
            if (rule == null) {
                continue;
            }
            String subExpression = build(env, rule);
            expression.append(subExpression);
        }
        return expression.toString();
    }

    public static String build(AviatorInput env, ExpressionRule rule) {
        if (rule == null) {
            return "";
        }
        Field field = Field.codeOf(rule.getField());
        Operator logicOperator = Operator.codeOf(rule.getLogicOperator());
        String subExpression = "";
        if (!isSkip(rule)) {
            boolean res = field.getDataCalFunction().doCal(env, rule);
            subExpression = Boolean.toString(res);
        }

        StringBuilder expressionBuilder = new StringBuilder();
        if (rule.isLeftBracket()) {
            expressionBuilder.append("(");
        }
        expressionBuilder.append(subExpression);
        if (rule.isRightBracket()) {
            expressionBuilder.append(")");
        }
        if (logicOperator != null) {
            expressionBuilder.append(logicOperator.getCharCode());
        }
        return expressionBuilder.toString();
    }

    private static boolean isSkip(ExpressionRule rule) {
        if (rule == null) {
            return true;
        }
        Field field = Field.codeOf(rule.getField());
        if (field == null) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws ScriptException {
        AviatorInput input = Field.getDefaultInput();
        ExpressionRule rule = new ExpressionRule();
        rule.setLeftBracket(true);
        rule.setField(Field.chInDate.getCode());
        rule.setOperator(Operator.ge.getCode());
        rule.setValue("2009-12-20 00:00:00:00");
        rule.setRightBracket(true);
        String exp = ExpressionBuilder.build(input, rule);
        System.out.println("日期表达式 = " + exp);
        boolean res = (boolean) AviatorEvaluator.execute(exp, input.toAviatorEnvMap());
        System.out.println("日期表达式res = " + res);

        rule.setLeftBracket(true);
        rule.setField(Field.chInWeek.getCode());
        rule.setOperator(Operator.not_in.getCode());
        rule.setValue("1,2,3,4,5,6,7");
        rule.setRightBracket(true);
        exp = ExpressionBuilder.build(input, rule);
        System.out.println("not_in表达式 = " + exp);
        res = (boolean) AviatorEvaluator.execute(exp, input.toAviatorEnvMap());
        System.out.println("not_in表达式res = " + res);

        System.out.println("env = " + input);

        // 在店星期
        rule.setLeftBracket(true);
        rule.setRightBracket(true);
        rule.setField(Field.atStoreWeek.getCode());
        rule.setOperator(Operator.in.getCode());
        rule.setValue("1,2,3,4");
        exp = ExpressionBuilder.build(input, rule);
        System.out.println("在店星期 表达式 = " + exp);
        res = (boolean) AviatorEvaluator.execute(exp, input.toAviatorEnvMap());
        System.out.println("在店星期 表达式res = " + res);

        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("Nashorn");
        String formula = "true && false";
        scriptEngine.put("a", true);
        scriptEngine.put("b", false);
        System.out.println(scriptEngine.eval(formula));

    }
}
