package com.tujia.myssm.web.diff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.tujia.myssm.api.enums.EnumOpType;
import com.tujia.myssm.api.model.HdsSaleBlacklist;
import com.tujia.myssm.common.date.format.DateFormatUtils;
import com.tujia.myssm.common.date.model.LocalDateRange;
import com.tujia.myssm.common.date.model.LocalDateRangeSet;
import com.tujia.myssm.core.diff.ObjectDiffTool;
import com.tujia.myssm.core.diff.properties.AtomicClass;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

/**
 *
 * @author: songlinl
 * @create: 2022/12/18 15:39
 */
public class ObjectDiffTest {

    static {
        AtomicClass.ATOMIC_CLASS_STR = new String[] { "com.tujia.framework.datetime.bean.ShortTime", "com.tujia.framework.datetime.bean.ShortDate",
                "java.time.LocalDateTime", "java.time.LocalDate" };
    }

    @Test
    public void test1() {
        LocalDateRangeSet rangeSet = new LocalDateRangeSet();
        rangeSet.add(new LocalDateRange());
        HdsSaleBlacklist base = HdsSaleBlacklist.builder().id(100L).dateRanges(rangeSet).createTime(LocalDateTime.now()).date(new Date()).timeList(
                Lists.newArrayList(LocalDateTime.MAX)).status(EnumOpType.SQLError).build();

        LocalDateRangeSet rangeSet1 = new LocalDateRangeSet();
        rangeSet1.add(new LocalDateRange(LocalDate.MAX, LocalDate.MIN));
        HdsSaleBlacklist work = HdsSaleBlacklist.builder().id(1000L).dateRanges(rangeSet1).createTime(LocalDateTime.MAX).date(
                DateFormatUtils.toDate(LocalDate.now())).timeList(Lists.newArrayList(LocalDateTime.now(), LocalDateTime.MAX)).status(
                EnumOpType.SQLExecute).build();

        String res = ObjectDiffTool.diff(base, work);
        System.out.println(res);
    }

    @Test
    public void testA() {
        LocalDateRangeSet rangeSet = new LocalDateRangeSet();
        rangeSet.add(new LocalDateRange());
        HdsSaleBlacklist base = HdsSaleBlacklist.builder().id(100L).dateRanges(rangeSet).createTime(LocalDateTime.now()).build();

        LocalDateRangeSet rangeSet1 = new LocalDateRangeSet();
        rangeSet1.add(new LocalDateRange(LocalDate.MAX, LocalDate.MIN));
        HdsSaleBlacklist work = HdsSaleBlacklist.builder().id(1000L).dateRanges(rangeSet1).createTime(LocalDateTime.MAX).build();

        DiffNode root = ObjectDifferBuilder.buildDefault().compare(work, base);
    }
}
