package com.tujia.myssm.service.idworker;

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
