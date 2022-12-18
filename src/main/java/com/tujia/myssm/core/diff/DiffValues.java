package com.tujia.myssm.core.diff;

/**
 * <p>作者 yaohua.liu
 * <p>日期 2017-11-10 11:59
 * <p>说明 新老数据值的存储
 */
public class DiffValues {

    private Object oldValue;

    private Object newValue;

    public DiffValues() {}

    /**
     *
     * @param oldValue 旧值
     * @param newValue 新值
     */
    public DiffValues(Object oldValue, Object newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
}
