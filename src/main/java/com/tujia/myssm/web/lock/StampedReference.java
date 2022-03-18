package com.tujia.myssm.web.lock;

import java.io.Serializable;
import javax.annotation.Nonnegative;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2022/01/28 17:50
 */
@Getter
@Setter
@NoArgsConstructor
public class StampedReference<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 8177310136323856078L;

    T obj;
    @Nonnegative
    long stamp; // ms

    public StampedReference(T obj) {
        this.obj = obj;
        this.stamp = System.currentTimeMillis();
    }
}