package com.tujia.myssm.web.diff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Test;
import com.tujia.myssm.api.model.HdsSaleBlacklist;
import com.tujia.myssm.common.date.model.LocalDateRange;
import com.tujia.myssm.common.date.model.LocalDateRangeSet;
import com.tujia.myssm.core.diff.ObjectDiffTool;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

/**
 *
 * @author: songlinl
 * @create: 2022/12/18 15:39
 */
public class ObjectDiffTest {

    @Test
    public void test1() {
        LocalDateRangeSet rangeSet = new LocalDateRangeSet();
        rangeSet.add(new LocalDateRange());
        HdsSaleBlacklist base = HdsSaleBlacklist.builder().id(100L).dateRanges(rangeSet).createTime(LocalDateTime.now()).build();

        LocalDateRangeSet rangeSet1 = new LocalDateRangeSet();
        rangeSet1.add(new LocalDateRange(LocalDate.MAX, LocalDate.MIN));
        HdsSaleBlacklist work = HdsSaleBlacklist.builder().id(1000L).dateRanges(rangeSet1).createTime(LocalDateTime.MAX).build();

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
