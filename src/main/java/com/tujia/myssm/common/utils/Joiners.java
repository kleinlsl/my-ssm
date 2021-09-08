package com.tujia.myssm.common.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.tujia.myssm.common.utils.Symbol.AND;
import static com.tujia.myssm.common.utils.Symbol.COLON;
import static com.tujia.myssm.common.utils.Symbol.COMMA;
import static com.tujia.myssm.common.utils.Symbol.DOT;
import static com.tujia.myssm.common.utils.Symbol.EQUAL;
import static com.tujia.myssm.common.utils.Symbol.HORIZONTAL;
import static com.tujia.myssm.common.utils.Symbol.OR;
import static com.tujia.myssm.common.utils.Symbol.PLUS;
import static com.tujia.myssm.common.utils.Symbol.POUND;
import static com.tujia.myssm.common.utils.Symbol.QUESTION;
import static com.tujia.myssm.common.utils.Symbol.SEMICOLON;
import static com.tujia.myssm.common.utils.Symbol.SLASH;
import static com.tujia.myssm.common.utils.Symbol.SPACE;
import static com.tujia.myssm.common.utils.Symbol.UNDERLINE;
import static com.tujia.myssm.common.utils.Symbol.VERTICAL;

/**
 * @author: songlinl
 * @create: 2021/09/08 15:09
 */
public final class Joiners {
    public static final Joiner HORIZONTAL_JOINER = Joiner.on(HORIZONTAL).skipNulls();
    public static final Joiner OR_JOINER = Joiner.on(OR).skipNulls();
    public static final Joiner AND_JOINER = Joiner.on(AND).skipNulls();
    public static final Joiner UNDERLINE_JOINER = Joiner.on(UNDERLINE).skipNulls();
    public static final Joiner SPACE_JOINER = Joiner.on(SPACE).skipNulls();
    public static final Joiner DOT_JOINER = Joiner.on(DOT).skipNulls();
    public static final Joiner COMMA_JOINER = Joiner.on(COMMA).skipNulls();
    public static final Joiner PLUS_JOINER = Joiner.on(PLUS).skipNulls();
    public static final Joiner COLON_JOINER = Joiner.on(COLON).skipNulls();
    public static final Joiner SEMICOLON_JOINER = Joiner.on(SEMICOLON).skipNulls();
    public static final Joiner SLASH_JOINER = Joiner.on(SLASH).skipNulls();
    public static final Joiner VERTICAL_JOINER = Joiner.on(VERTICAL).skipNulls();
    public static final Joiner QUESTION_JOINER = Joiner.on(QUESTION).skipNulls();
    public static final Joiner POUND_JOINER = Joiner.on(POUND).skipNulls();
    public static final Joiner.MapJoiner OR_STAND_MAP_JOINER = Joiner.on(SEMICOLON).withKeyValueSeparator(VERTICAL);
    public static final Joiner.MapJoiner PLUS_STAND_MAP_JOINER = Joiner.on(SEMICOLON).withKeyValueSeparator(PLUS);
    public static final Joiner.MapJoiner AND_EQUAL_MAP_JOINER = Joiner.on(AND).withKeyValueSeparator(EQUAL);

    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        String res = Joiners.OR_STAND_MAP_JOINER.join(map);
        System.err.println(res);
    }
}
