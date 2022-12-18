package com.tujia.myssm.core.diff.format;

import java.time.LocalDate;
import com.tujia.myssm.core.diff.formatter.ValueFormatter;

/**
 *
 * @author: songlinl
 * @create: 2022/12/18 16:08
 */
public class LocalDateFormatter implements ValueFormatter<LocalDate> {

    @Override
    public String format(LocalDate value) {
        return value.toString();
    }
}
