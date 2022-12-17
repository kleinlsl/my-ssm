package com.tujia.myssm.common.base.monitor;

/**
 * 监控基础描述
 * Created by bowen.ma on 19/2/2.
 */
public interface MRegistry {

    /**
     * 获取名字
     *
     * @return String
     */
    String key();

    /**
     * 获取描述信息
     *
     * @return String
     */
    String desc();

}
