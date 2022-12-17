package com.tujia.myssm.core;

/**
 * 从机票的监控组件移植过来的，只是接口兼容
 * <p>
 * created on 12-11-23 下午2:59
 *
 * @version $Id: QMonitor.java 9790 2012-12-21 10:18:53Z build $
 */

public class TMonitor {

    /**
     * 计算平均时间
     *
     * @param name
     * @param time
     */
    public static void recordOne(String name, long time) {
    }

    /**
     * 计算平均时间, P90, P95, P99
     *
     * @param name
     * @param time
     */
    public static void recordOneWithQuantile(String name, long time) {
    }

    /**
     * 计数器
     *
     * @param name 要自增的key
     */
    public static void recordOne(String name) {
    }

    /**
     * 带有摘要的计数器-仅公共组件TC通知使用
     *
     * @param name 要自增的key
     */
    public static void recordOneWithDesc(String name, String desc) {
    }

    /**
     * 计数器
     *
     * @param name 要自减的key
     */
    public static void decrRecord(String name) {
    }

    /**
     * @param name
     * @param count
     */
    public static void incrRecord(String name, long count) {
    }

    /**
     * @param name
     * @param count
     * @param time
     */
    public static void incrRecord(String name, long count, long time) {
    }

    public static void incrRecordWithQuantile(String name, long count, long time) {
    }

    /**
     * 绝对值监控
     *
     * @param name 监控的key
     * @param size 要set的值
     */
    public static void recordSize(String name, long size) {

    }

    /**
     * 绝对值监控
     *
     * @param name  监控的key
     * @param count 对绝对值增加count
     */
    public static void recordValue(String name, long count) {
    }

    private static String makeKey(String prefix, String key, String suffix) {
        return prefix + "." + key + suffix;
    }

}

