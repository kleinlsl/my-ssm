package com.tujia.myssm.core.diff.utils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.MessageFormat;
import com.google.common.primitives.Primitives;

//import com.tujia.framework.money.Money;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Description: TPropertiesEditor
 *
 * just a convert
 *
 * @author yushen.ma
 * @version 2015-04-24 14:26
 */
public enum TPropertiesEditor {

    INSTANCE;

    //    final private Logger logger = LoggerFactory.getLogger(getClass());

    public Object apply(String plainValue, Class<?> targetValueClass) {
        //no need for check,the difference whether check is different exception thrown
        //Assert.isTrue(isSupport(targetValueClass));
        if (plainValue == null || plainValue == "") {
            throw new IllegalArgumentException("[Assertion failed] - this expression must be true");
        }
        plainValue = plainValue.trim();
        // wrap, this is important, cause of primitive class is such fucking special
        if (targetValueClass.isPrimitive()) {
            targetValueClass = Primitives.wrap(targetValueClass);
        }
        if (targetValueClass == Integer.class) {
            return Integer.parseInt(plainValue);
        } else if (targetValueClass == Long.class) {
            return Long.parseLong(plainValue);
        } else if (targetValueClass == String.class) {
            return plainValue;
        } else if (Double.class == targetValueClass) {
            return Double.parseDouble(plainValue);
        } else if (Float.class == targetValueClass) {
            return Float.parseFloat(plainValue);
        } else if (Void.class == targetValueClass) {
            return Void.TYPE;
        } else if (Short.class == targetValueClass) {
            return Short.parseShort(plainValue);
        } else if (Character.class == targetValueClass) {
            return plainValue.charAt(0);
        } else if (Byte.class == targetValueClass) {
            return plainValue.getBytes()[0];
        } else if (BigDecimal.class == targetValueClass) {
            return new BigDecimal(plainValue);
        }/* else if (Money.class == targetValueClass) {
            return new Money(new BigDecimal(plainValue));
        } */ else if (Boolean.class == targetValueClass) {
            return Boolean.valueOf(plainValue);
        } else {
            // find constructor with a string arg
            try {
                Constructor strConstructor = targetValueClass.getConstructor(String.class);
                if (strConstructor != null) {
                    return strConstructor.newInstance(plainValue);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        MessageFormat.format("UNSUPPORTED TYPE : \"{0}\" and VALUE : \"{1}\"", targetValueClass, plainValue), e);
            }
        }
        throw new IllegalArgumentException(MessageFormat.format("UNSUPPORTED TYPE : \"{0}\" and VALUE : \"{1}\"", targetValueClass, plainValue));
    }

    public boolean isSupport(Class<?> clazz) {
        if (clazz.isPrimitive()) { // 费劲
            clazz = Primitives.wrap(clazz);
        }
        // @see http://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.7
        if (clazz == Double.class || clazz == Long.class) {
            /*logger.info("long and double is not safe to replace in Java Language memory model"
                    + " For the purposes of the Java programming language memory model, "
                    + " a single write to a non-volatile long or double value is treated as two separate writes: one to each 32-bit half."
                    + " This can result in a situation where a thread sees the first 32 bits of a 64-bit value from one write,"
                    + " and the second 32 bits from another write.\n" + "\n ");*/
            return false;
        }
        return String.class == clazz || BigDecimal.class == clazz /*|| Money.class == clazz*/ || Primitives.isWrapperType(clazz);
    }
}
