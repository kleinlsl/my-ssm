package com.tujia.myssm.api.model.base;

import java.util.HashSet;
import com.tujia.myssm.common.utils.Joiners;
import com.tujia.myssm.common.utils.Splitters;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 17:12
 */
public class LocalDateRangeSet extends HashSet<LocalDateRange> {
    private static final long serialVersionUID = -3766831400909007169L;

    public static LocalDateRangeSet parse(String value) {
        LocalDateRangeSet dateRanges = new LocalDateRangeSet();
        for (String range : Splitters.COMMA_SPLITTER.split(value)) {
            dateRanges.add(LocalDateRange.parse(range));
        }
        return dateRanges;
    }

    @Override
    public String toString() {
        return Joiners.COMMA_JOINER.join(this);
    }

}
