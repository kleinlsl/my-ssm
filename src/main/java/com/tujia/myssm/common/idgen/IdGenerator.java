package com.tujia.myssm.common.idgen;

/**
 *
 * @author: songlinl
 * @create: 2022/12/13 10:35
 */
public interface IdGenerator {

    /**
     * next id long
     * @return
     */
    Long nextId();

    /**
     * next code string
     * @return
     */
    String nextCode();

}
