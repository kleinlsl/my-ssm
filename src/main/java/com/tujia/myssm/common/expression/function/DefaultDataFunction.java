package com.tujia.myssm.common.expression.function;

import com.tujia.myssm.common.expression.enums.DataType;
import com.tujia.myssm.common.expression.enums.Field;
import com.tujia.myssm.common.expression.enums.Operator;
import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;
import com.tujia.myssm.common.expression.operation.Operation;
import com.tujia.myssm.utils.base.Splitters;

/**
 *
 * @author: songlinl
 * @create: 2023/01/04 19:07
 */
public class DefaultDataFunction implements DataCalFunction {

    private static final DefaultDataFunction INSTANCE = new DefaultDataFunction();

    private DefaultDataFunction() {
    }

    public static DefaultDataFunction getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean doCal(AviatorInput env, ExpressionRule rule) {
        Field field = Field.codeOf(rule.getField());
        if (field == null) {
            return false;
        }
        Operator operator = Operator.codeOf(rule.getOperator());
        DataType dataType = field.getDataType();
        String value = rule.getValue();
        if (operator.equals(Operator.in)) {
            return in(field, env, value);
        }
        if (operator.equals(Operator.not_in)) {
            return notIn(field, env, value);
        }
        Operation dataOperation = dataType.getOperation();
        Object envVal = env.getByField(field);
        switch (operator) {
            case eq:
                return dataOperation.compare(envVal, value) == 0;
            case ge:
                return dataOperation.compare(envVal, value) >= 0;
            case gt:
                return dataOperation.compare(envVal, value) > 0;
            case lt:
                return dataOperation.compare(envVal, value) < 0;
            case le:
                return dataOperation.compare(envVal, value) <= 0;
            case neq:
                return dataOperation.compare(envVal, value) != 0;
            default:
                throw new UnsupportedOperationException(String.format("不支持的操作符：%s", operator.getDesc()));
        }
    }

    private boolean notIn(Field field, AviatorInput env, String value) {
        boolean result = true;
        DataType dataType = field.getDataType();
        Object envVal = env.getByField(field);
        Operation dataOperation = dataType.getOperation();
        for (String val : Splitters.COMMA_SPLITTER.split(value)) {
            if (dataOperation.compare(envVal, val) == 0) {
                result = false;
            }
        }
        return result;
    }

    private boolean in(Field field, AviatorInput env, String value) {
        boolean result = false;
        DataType dataType = field.getDataType();
        Object envVal = env.getByField(field);
        Operation dataOperation = dataType.getOperation();
        for (String val : Splitters.COMMA_SPLITTER.split(value)) {
            if (dataOperation.compare(envVal, val) == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

}
