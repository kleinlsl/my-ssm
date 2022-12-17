package com.tujia.myssm.core.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lizuju on 2017/9/21.
 */
public class DefaultRegistry<I> implements Registry<I> {

    private final Map<String, I> map;

    DefaultRegistry(final Map<String, I> map) {
        super();
        this.map = new HashMap<String, I>(map);
    }

    @Override
    public Iterator<Map.Entry<String, I>> iterator() {
        return this.map.entrySet().iterator();
    }

    @Override
    public boolean isEmpty() {
        return this.map == null || this.map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
