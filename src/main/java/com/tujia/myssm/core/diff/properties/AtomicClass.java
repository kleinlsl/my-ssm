package com.tujia.myssm.core.diff.properties;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * <p>作者 yaohua.liu
 * <p>日期 2017-11-24 17:44
 * <p>说明 原子类配置：不比较类下面的所有字段，把类作为整体进行diff,如：com.tujia.framework.datetime.bean.ShortTime
 * <p>原子类名使用类的绝对路径
 */
public class AtomicClass {
    public static String[] ATOMIC_CLASS_STR = new String[] { "com.tujia.framework.datetime.bean.ShortTime",
            "com.tujia.framework.datetime.bean.ShortDate", "java.time.LocalDateTime" };

    /**
     * 检查类是否是原子类
     *
     * @param classPath 类
     * @return true 是，false 不是
     */
    public static boolean checkAtomicClass(String classPath) {
        if (Strings.isNullOrEmpty(classPath)) {
            return false;
        }
        if (ATOMIC_CLASS_STR == null || ATOMIC_CLASS_STR.length == 0) {
            return false;
        }
        for (String path : ATOMIC_CLASS_STR) {
            if (Strings.isNullOrEmpty(path)) {
                continue;
            }
            if (Objects.equal(path, classPath)) {
                return true;
            }
        }
        return false;
    }
}
