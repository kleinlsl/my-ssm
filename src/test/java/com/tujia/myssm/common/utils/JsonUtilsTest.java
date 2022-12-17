package com.tujia.myssm.common.utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import org.junit.Test;
import com.tujia.myssm.api.model.wx.WxDrainageDetail;
import com.tujia.myssm.common.date.DateTimeRange;
import com.tujia.myssm.utils.base.JsonUtils;
import io.vavr.control.Try;

/**
 *
 * @author: songlinl
 * @create: 2021/09/28 13:59
 */
public class JsonUtilsTest {

    @Test
    public void testDateTimeRange() {
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setStart(LocalDateTime.now());
        dateTimeRange.setEnd(LocalDateTime.now());
        dateTimeRange.setCurrentTime(new Date());
        String json = JsonUtils.toJson(dateTimeRange);
        System.out.println("json = " + json);
        dateTimeRange = JsonUtils.readValue(json, DateTimeRange.class);
        System.out.println("dateTimeRange = " + dateTimeRange);
    }

    @Test
    public void main() {

        WxDrainageDetail wxDrainageDetail = new WxDrainageDetail();
        Date res = Try.ofSupplier(wxDrainageDetail::getFromTime).getOrElse(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(res);
        System.out.println("stringBuilder = " + stringBuilder);
        System.out.println("Try.ofSupplier(new RedPacketConfigSub().getBizFlow()).getOrElse(0) = " + res);
        int val = (int) Optional.ofNullable(null).orElse(11);
        System.out.println("val = " + val);

    }
}