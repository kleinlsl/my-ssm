package com.tujia.myssm.common.expression.function;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import com.tujia.myssm.common.date.DateUtils;
import com.tujia.myssm.common.expression.enums.Operator;
import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;

/**
 * 在店日期：入离店中的每一天均需满足条件
 * @author: songlinl
 * @create: 2023/01/05 10:57
 */
public class AtStoreDateFunction implements DataCalFunction {
    private static final AtStoreDateFunction INSTANCE = new AtStoreDateFunction();
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AtStoreDateFunction() {
    }

    public static AtStoreDateFunction getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean doCal(AviatorInput env, ExpressionRule rule) {
        LocalDateTime valueDate = LocalDateTime.parse(rule.getValue(), DTF);
        Date chInDate = env.getChInDate();
        Date chOutDate = env.getChOutDate();

        LocalDate localChInDate = DateUtils.toLocalDate(chInDate);
        LocalDate localChOutDate = DateUtils.toLocalDate(chOutDate);
        LocalDate localValueDate = valueDate.toLocalDate();

        Operator operator = Operator.codeOf(rule.getOperator());
        switch (operator) {
            case gt:
                return gt(localChInDate, localChOutDate, localValueDate);
            case ge:
                return ge(localChInDate, localChOutDate, localValueDate);
            case eq:
                return eq(localChInDate, localChOutDate, localValueDate);
            case le:
                return le(localChInDate, localChOutDate, localValueDate);
            case lt:
                return lt(localChInDate, localChOutDate, localValueDate);
            case neq:
                return neq(localChInDate, localChOutDate, localValueDate);
            default:
                throw new UnsupportedOperationException(String.format("在店日期不支持操作符：%s", operator.getDesc()));
        }
    }

    /**
     * 在店日期不等于 value => value ∈ (-∞，chInDate) || [chOutDate，+∞)
     * @param chInDate
     * @param chOutDate
     * @param value
     * @return
     */
    private boolean neq(LocalDate chInDate, LocalDate chOutDate, LocalDate value) {
        return value.isBefore(chInDate) || value.isAfter(chOutDate) || value.equals(chOutDate);
    }

    /**
     * 在店日期大于 value => value ∈ （-∞，chInDate)
     * @param chInDate
     * @param chOutDate
     * @param value
     * @return
     */
    private boolean gt(LocalDate chInDate, LocalDate chOutDate, LocalDate value) {
        return value.isBefore(chInDate);
    }

    /**
     * 在店日期大于等于 value => value ∈ （-∞，chInDate]
     * @param chInDate
     * @param chOutDate
     * @param value
     * @return
     */
    private boolean ge(LocalDate chInDate, LocalDate chOutDate, LocalDate value) {
        return gt(chInDate, chOutDate, value) || value.equals(chInDate);
    }

    /**
     * 在店日期等于 value => value==chInDate && (chInDate + 1day) == chOutDate
     * @param chInDate
     * @param chOutDate
     * @param value
     * @return
     */
    private boolean eq(LocalDate chInDate, LocalDate chOutDate, LocalDate value) {
        return value.equals(chInDate) && chInDate.plusDays(1).equals(chOutDate);
    }

    /**
     * 在店日期小于 value => value ∈ [chOutDate，+∞)
     * @param chInDate
     * @param chOutDate
     * @param value
     * @return
     */
    private boolean lt(LocalDate chInDate, LocalDate chOutDate, LocalDate value) {
        return value.isAfter(chOutDate) || value.equals(chOutDate);
    }

    /**
     * 在店日期小于 value => value ∈ [chOutDate，+∞) || (chOutDate - 1day) == value
     * @param chInDate
     * @param chOutDate
     * @param value
     * @return
     */
    private boolean le(LocalDate chInDate, LocalDate chOutDate, LocalDate value) {
        return lt(chInDate, chOutDate, value) || chOutDate.minusDays(1L).equals(value);
    }
}
