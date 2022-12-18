package com.tujia.myssm.core.diff.extend;

import java.lang.reflect.Field;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tujia.myssm.core.diff.annotation.DiffEntity;
import com.tujia.myssm.core.diff.danielbechler.diff.identity.IdentityStrategy;

/**
 * @author jianhong.li Date: 2019-10-17 Time: 5:04 PM
 * @version $Id$
 */
public class CollectionIdentityStrategy implements IdentityStrategy {

    public static final CollectionIdentityStrategy INSTANCE = new CollectionIdentityStrategy();
    private static final Logger logger = LoggerFactory.getLogger(CollectionIdentityStrategy.class);

    @Override
    public boolean equals(Object working, Object base) {

        if (working == null && base != null) {
            return false;
        }
        if (working != null && base == null) {
            return false;
        }
        if (working == base) {
            return true;
        }

        Class<?> tmpClass = working.getClass();

        // FIXME 这里只做了相同子类的判定.需要更新为子类与父类都是可以才行.
        if (tmpClass == base.getClass()) {
            while (tmpClass != null && !tmpClass.getName().toLowerCase().equals("java.lang.object")) {

                for (Field field : tmpClass.getDeclaredFields()) {

                    if (field.getAnnotation(DiffEntity.class) != null) {
                        field.setAccessible(true);
                        try {
                            Object workingDiffId = field.get(working);
                            Object baseDiffId = field.get(base);
                            if ((workingDiffId != null && baseDiffId != null) && ObjectUtils.equals(workingDiffId, baseDiffId)) {
                                return true;
                            }
                        } catch (IllegalAccessException e) {
                            logger.warn("searchAndReadDiffEntityIdError,FieldName={}", field.getName(), e);
                            return false;
                        }
                    }
                }
                tmpClass = tmpClass.getSuperclass();
            }
        }
        return ObjectUtils.equals(working, base);
    }
}
