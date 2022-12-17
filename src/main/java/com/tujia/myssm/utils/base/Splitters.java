package com.tujia.myssm.utils.base;

import com.google.common.base.Splitter;

import java.util.Map;

import static com.tujia.myssm.utils.base.Symbol.COLON;
import static com.tujia.myssm.utils.base.Symbol.COMMA;
import static com.tujia.myssm.utils.base.Symbol.POUND;
import static com.tujia.myssm.utils.base.Symbol.SEMICOLON;
import static com.tujia.myssm.utils.base.Symbol.UNDERLINE;
import static com.tujia.myssm.utils.base.Symbol.VERTICAL;

/**
 * @author: songlinl
 * @create: 2021/09/08 15:19
 */
public final class Splitters {
    public static final Splitter COMMA_SPLITTER = Splitter.on(COMMA).omitEmptyStrings().trimResults();
    public static final Splitter POUND_SPLITTER = Splitter.on(POUND).omitEmptyStrings().trimResults();
    public static final Splitter VERTICAL_SPLITTER = Splitter.on(VERTICAL).omitEmptyStrings().trimResults();
    public static final Splitter COLON_SPLITTER = Splitter.on(COLON).omitEmptyStrings().trimResults();
    public static final Splitter UNDERLINE_SPLITTER = Splitter.on(UNDERLINE).omitEmptyStrings().trimResults();

    public static final Splitter.MapSplitter OR_STAND_MAP_SPLITTER = Splitter.on(SEMICOLON).omitEmptyStrings().trimResults().withKeyValueSeparator(VERTICAL);

    public static void main(String[] args) {
        String source = "key1|value1;key2|value2";
        Map<String, String> map = Splitters.OR_STAND_MAP_SPLITTER.split(source);
        System.out.println("map = " + map);
    }
}
