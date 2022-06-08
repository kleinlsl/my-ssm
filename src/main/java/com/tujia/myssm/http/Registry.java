package com.tujia.myssm.http;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lizuju on 2017/9/21.
 */
public interface Registry<I> {

    public Iterator<Map.Entry<String, I>> iterator();

    public boolean isEmpty();

    public int size();
}
