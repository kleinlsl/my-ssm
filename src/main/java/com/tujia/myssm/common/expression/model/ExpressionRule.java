package com.tujia.myssm.common.expression.model;

import java.io.Serializable;
import com.tujia.myssm.common.expression.enums.Field;
import com.tujia.myssm.common.expression.enums.Operator;
import lombok.Data;

/**
 *
 * @author: songlinl
 * @create: 2022/12/28 13:35
 */
@Data
public class ExpressionRule implements Serializable {
    private static final long serialVersionUID = 3234866639257790783L;
    /**
     * 左括号
     */
    private boolean leftBracket;
    /**
     * 数据项 {@link Field#getCode()}
     */
    private Integer field;
    /**
     * 非逻辑运算符：{@link Operator#isLogic()} == false
     * 取值：{@link Operator#getCode()}
     */
    private Integer operator;

    /**
     * 值:类型参照 {@link ExpressionRule#field} 枚举值 {@link Field#getDataType()}
     */
    private String value;

    /**
     * 右括号
     */
    private boolean rightBracket;

    /**
     * 逻辑运算符：{@link Operator#isLogic()} == true
     *      取值：{@link Operator#getCode()}
     */
    private Integer logicOperator;

    @Override
    public String toString() {
        Field field = Field.codeOf(this.getField());
        Operator operator = Operator.codeOf(this.getOperator());
        Operator logicOperator = Operator.codeOf(this.getLogicOperator());
        String value = this.getValue();
        StringBuilder builder = new StringBuilder();
        if (this.leftBracket) {
            builder.append("(");
        }
        if (field != null) {
            builder.append(field.getDesc());
        }
        if (operator != null) {
            builder.append(operator.getCharCode());
        }
        if (value != null) {
            builder.append(value);
        }
        if (this.rightBracket) {
            builder.append(")");
        }
        if (logicOperator != null) {
            builder.append(logicOperator.getCharCode());
        }
        return builder.toString();
    }
}
