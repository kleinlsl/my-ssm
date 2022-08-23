package com.tujia.myssm.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class RegistryBuilder<I> {

    private final boolean isRepeatable;
    private Map<String, I> items;
    private List<Entry<String, I>> list;

    private RegistryBuilder(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
        if (isRepeatable) {
            list = new ArrayList<Entry<String, I>>();
        } else {
            items = new HashMap<String, I>();
        }
    }

    public static <I> RegistryBuilder<I> createDefault() {
        return new RegistryBuilder<I>(false);
    }

    public static <I> RegistryBuilder<I> createRepeatable() {
        return new RegistryBuilder<I>(true);
    }

    public RegistryBuilder<I> registerAll(Map<String, I> map) {
        checkNotNull(map);
        if (isRepeatable) {
            map.forEach((key, value) -> {
                list.add(new SimpleEntry<String, I>(key, value));
            });
        } else {
            items.putAll(map);
        }
        return this;
    }

    public RegistryBuilder<I> register(final String id, final I item) {
        checkArgument(StringUtils.isNotEmpty(id));
        checkNotNull(item);
        if (isRepeatable) {
            list.add(new SimpleEntry<String, I>(id, item));
        } else {
            items.put(id, item);
        }
        return this;
    }

    public Registry<I> build() {
        if (isRepeatable) {
            return new RepeatableRegistry<I>(list);
        } else {
            return new DefaultRegistry<I>(items);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("RegistryBuilder{").append(items).append("|").append(list).append("|").append(isRepeatable).append("}").toString();
    }

}
