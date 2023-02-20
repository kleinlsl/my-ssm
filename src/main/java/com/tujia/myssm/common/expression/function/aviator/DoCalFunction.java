package com.tujia.myssm.common.expression.function.aviator;

import java.util.Map;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import com.tujia.myssm.common.expression.enums.Field;
import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;

/**
 * 自定义函数
 * @author: songlinl
 * @create: 2023/02/17 14:44
 */
public class DoCalFunction extends AbstractFunction {
    private static final long serialVersionUID = 4103096224551391708L;
    private static final DoCalFunction INSTANCE = new DoCalFunction();

    private DoCalFunction() {
    }

    public static DoCalFunction getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "doCal";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject fieldName, AviatorObject operator, AviatorObject value) {
        ExpressionRule rule = new ExpressionRule();
        if (fieldName instanceof AviatorLong) {
            rule.setField((int) ((AviatorLong) fieldName).longValue());
        }
        if (operator instanceof AviatorLong) {
            rule.setOperator((int) ((AviatorLong) operator).longValue());
        }
        if (value instanceof AviatorString) {
            rule.setValue((String) value.getValue(env));
        }
        Field field = Field.codeOf(rule.getField());
        AviatorInput input = (AviatorInput) env.get(AviatorInput.AVIATOR_INPUT);
        boolean sub = field.getDataCalFunction().doCal(input, rule);

        return AviatorBoolean.valueOf(sub);
    }
}
