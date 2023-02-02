package com.tujia.myssm.api.aviator.function;

import java.time.LocalTime;
import java.util.Map;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 *
 * 时间比较函数：
 * hh:mm:ss
 * @author: songlinl
 * @create: 2022/12/28 23:10
 */
public class TimeCompareFunction extends AbstractFunction {
    @Override
    public String getName() {
        return "time_compare";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String left = FunctionUtils.getStringValue(arg1, env);
        String right = FunctionUtils.getStringValue(arg2, env);
        LocalTime leftTime = LocalTime.parse(left);
        LocalTime rightTime = LocalTime.parse(right);
        if (leftTime.isBefore(rightTime)) {
            return AviatorLong.valueOf(-1L);
        } else if (leftTime.isAfter(rightTime)) {
            return AviatorLong.valueOf(1L);
        } else {
            return AviatorLong.valueOf(0L);
        }
    }
}


