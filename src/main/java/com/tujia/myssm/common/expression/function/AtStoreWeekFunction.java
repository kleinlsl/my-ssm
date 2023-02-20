package com.tujia.myssm.common.expression.function;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;
import com.google.common.collect.Sets;
import com.tujia.myssm.common.date.DateUtils;
import com.tujia.myssm.common.expression.enums.Field;
import com.tujia.myssm.common.expression.enums.Operator;
import com.tujia.myssm.common.expression.model.AviatorInput;
import com.tujia.myssm.common.expression.model.ExpressionRule;
import com.tujia.myssm.utils.base.Splitters;

/**
 * 在店星期：入离店日期中每一天，均需满足条件
 * @author: songlinl
 * @create: 2023/01/05 10:59
 */
public class AtStoreWeekFunction implements DataCalFunction {
    private static final AtStoreWeekFunction INSTANCE = new AtStoreWeekFunction();

    private AtStoreWeekFunction() {
    }

    public static AtStoreWeekFunction getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean doCal(AviatorInput env, ExpressionRule rule) {
        Date chInDate = env.getChInDate();
        Date chOutDate = env.getChOutDate();
        LocalDateTime localChInDate = DateUtils.toLocalDateTime(chInDate);
        LocalDateTime localChOutDate = DateUtils.toLocalDateTime(chOutDate);
        Field field = Field.codeOf(rule.getField());
        Operator operator = Operator.codeOf(rule.getOperator());
        long atStoreDay = DateUtils.diffDays(localChInDate, localChOutDate);
        switch (operator) {
            case in:
            case not_in:
                return handleInNotIn(localChInDate, atStoreDay, field, rule.getValue(), operator);
            default:
                throw new UnsupportedOperationException(String.format("在店星期不支持操作符：%s", operator.getDesc()));
        }
    }

    /**
     *
     * @param chInDate      入住日期
     * @param atStoreDay    在店天数
     * @return
     */
    public boolean handleInNotIn(LocalDateTime chInDate, long atStoreDay, Field field, String value, Operator operator) {
        Set<Integer> weekSet = Sets.newHashSet();
        Iterable<String> values = Splitters.COMMA_SPLITTER.split(value);
        for (String val : values) {
            if (!field.getDataType().getOperation().validate(val)) {
                throw new UnsupportedOperationException(String.format("在店星期不支持值：%s", val));
            }
            Integer week = NumberUtils.toInt(val);
            weekSet.add(week);
        }
        if (operator.equals(Operator.in)) {
            return allMatch(chInDate, atStoreDay, weekSet);
        }
        if (operator.equals(Operator.not_in)) {
            return anyMatch(chInDate, atStoreDay, weekSet);
        }
        return false;
    }

    /**
     * [chInDate,chInDate+atStoreDay] 每一天均在 Set 集合
     * @param chInDate
     * @param atStoreDay
     * @param weekSet
     * @return
     */
    private boolean allMatch(LocalDateTime chInDate, long atStoreDay, Set<Integer> weekSet) {
        DayOfWeek inWeek = chInDate.getDayOfWeek();
        boolean isAllMatch = true;
        for (int i = 0; i < atStoreDay; i++) {
            Integer atStoreWeek = inWeek.plus(i).getValue();
            if (!weekSet.contains(atStoreWeek)) {
                isAllMatch = false;
                break;
            }
        }
        return isAllMatch;
    }

    /**
     * [chInDate,chInDate+atStoreDay] 存在一天在 Set 集合
     * @param chInDate
     * @param atStoreDay
     * @param weekSet
     * @return
     */
    private boolean anyMatch(LocalDateTime chInDate, long atStoreDay, Set<Integer> weekSet) {
        DayOfWeek inWeek = chInDate.getDayOfWeek();
        boolean isAnyMatch = false;
        for (int i = 0; i < atStoreDay; i++) {
            Integer atStoreWeek = inWeek.plus(i).getValue();
            if (weekSet.contains(atStoreWeek)) {
                isAnyMatch = true;
                break;
            }
        }
        return isAnyMatch;
    }

}
