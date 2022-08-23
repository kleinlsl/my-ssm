package com.tujia.myssm.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by lizuju on 2017/9/21.
 */
public class RepeatableRegistry<I> implements Registry<I> {

    private final List<Entry<String, I>> list;

    RepeatableRegistry(final List<Entry<String, I>> list) {
        super();
        this.list = new ArrayList<Entry<String, I>>(list);
    }

    @Override
    public Iterator<Entry<String, I>> iterator() {
        return list.iterator();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
